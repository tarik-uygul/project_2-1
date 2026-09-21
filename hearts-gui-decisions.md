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
Cinzel Bold for titles and headings (48px and 28px) and Inter Tight Regular for body text (22px)
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

Four real people share one laptop (hot-seat), so there is no single "local" player: the GUI draws the table from the point of view of whoever is sitting at the laptop and hides everything behind a "X's turn - tap to reveal" gate between players. Two interfaces in `com.example.gui.state` are the whole contract; the GUI never changes game state itself.

**`GameStateView` (read-only, GUI reads):**

- `int getPlayerCount()`
- `String getPlayerName(int playerIndex)`
- `List<Card> getPlayerHand(int playerIndex)` - full contents; the GUI only asks for the player currently at the laptop
- `int getHandSize(int playerIndex)` - shown to everyone else as face-down cards
- `int getCurrentPlayerIndex()` - whose turn it is
- `List<Card> getCurrentTrickCards()` / `List<Integer> getCurrentTrickPlayers()` - parallel lists, cards played this trick and who played each. A finished trick should stay visible until the next card starts a new one (the next player sees it behind the gate)
- `int getPlayerScore(int playerIndex)`
- `int getPassTarget(int playerIndex)` - who this player passes to this round, or -1 on a no-passing round (the GUI then skips passing)
- `List<Card> getReceivedCards(int playerIndex)` - the 3 cards received in passing

**`GameActions` (GUI calls, logic implements):**

- `void passCards(int playerIndex, List<Card> cards)` - player picked these 3 cards
- `boolean playCard(int playerIndex, Card card)` - false = rejected, the GUI shows an "invalid move" message

**Flow the GUI runs:** Setup (4 names) -> for each player: gate, pick 3, Confirm -> for each player: gate, see the 3 received cards -> play (each turn behind a gate) -> Round End -> next round starts with passing again. `DemoGameStateView` implements both interfaces with no rules, as a stand-in until the real game logic does; delete it then.

### 3. PassingScreen reuses Person A's card component

PassingScreen's 3-card selection UI reuses Person A's card-rendering component (the same visual card + the same selected-card highlight style from the mockup) rather than Person B building separate card visuals. Person B only adds the selection logic (tap to select/deselect, enforce exactly 3, confirm button) on top of it — avoids building and maintaining two card renderers that could drift apart visually.
