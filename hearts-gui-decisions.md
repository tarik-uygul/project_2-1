# Hearts – GUI Decisions

## 1. Card Style

Flat / vector-style cards: crisp suit icons, bold outlines, subtle drop shadow.
Not photorealistic textures, so we can be faster.

## 2. Color Palette

Felt-table theme. Four core colors, used consistently.
Only introduce colors if you update this doc!

| Purpose              | Color     |
|----------------------|-----------|
| Table background     | `#1B4332` |
| Your turn            | `#E9C46A` |
| Invalid move         | `#E76F51` |
| Selected card        | `#4CC9F0` |
| Neutral / text on dark | `#F1FAEE` |

## 3. Typography

Only one display face, one UI face — no third fonts without updating this doc.

Import both as `.ttf`, generate `BitmapFont`s via libGDX's FreeType extension (`gdx-freetype`) so text stays crisp at the target resolution instead of pixelating.

## 4. Viewport & Resolution

| Setting | Value |
|---------|-------|
| Base virtual resolution | 1280 x 720 |
| Viewport type | FitViewport |
| Rule | Every screen is built and positioned against this 1280 x 720 coordinate system — no screen-specific resolutions |

## 5. Asset Folders & Naming

```
assets/
├── cards/   (card_hearts_queen.png, card_back.png)
├── ui/      (button_primary.png, icon_exit.png)
├── fonts/   (title.ttf, body.ttf)
└── sfx/     (later...)
```

## 6. Scene2D Skin

Start from a free premade skin — don't hand-roll widget styling from scratch.

- Base: libGDX's default `uiskin`, or a free pack
- Restyle button, label, and text-field colors to the palette above
- Apply the display/UI fonts above to the skin's font entries

## Handoff Points

### 1. TurnGateScreen → GameScreen: overlay, not replace

TurnGateScreen renders as an overlay on top of GameScreen (a semi-transparent "Player X's turn — tap to reveal" panel), rather than being a separate Screen that fully replaces it. This means GameScreen keeps running underneath (table state stays rendered) and TurnGateScreen is really just a gate widget/state, not a full Screen swap. Simpler to reason about than tearing down and rebuilding GameScreen every turn.

### 2. What GameScreen needs from GameState

Agree on a minimal read-only interface GameScreen pulls from: current trick (cards played this round + who played them), each player's hand size (not contents, for opponents), whose turn it is, and current scores. Person A builds GameScreen's rendering against this interface; Person B's engine just needs to keep it updated. Write this as a short list of fields/getters in the shared doc so neither of you guesses at the other's data shape.

Defined as `com.example.gui.state.GameStateView` (GUI side, `src/main/java/com/example/gui/state/GameStateView.java`). Convention: `playerIndex` 0 is always the local/human player. GameScreen renders against this interface only:

- `int getPlayerCount()`
- `String getPlayerName(int playerIndex)`
- `List<Card> getPlayerHand(int playerIndex)` — full contents, local player only
- `int getHandSize(int playerIndex)` — count only, used for opponents
- `int getCurrentPlayerIndex()` — whose turn it is
- `List<Card> getCurrentTrickCards()` / `List<Integer> getCurrentTrickPlayers()` — parallel lists, cards played this trick and who played each
- `int getPlayerScore(int playerIndex)`

Person B implements this on top of the real `GameState`/`PlayerState` classes; the GUI never mutates state through it, only reads.

### 3. PassingScreen reuses Person A's card component

PassingScreen's 3-card selection UI reuses Person A's card-rendering component (the same visual card + the same selected-card highlight style from the mockup) rather than Person B building separate card visuals. Person B only adds the selection logic (tap to select/deselect, enforce exactly 3, confirm button) on top of it — avoids building and maintaining two card renderers that could drift apart visually.
