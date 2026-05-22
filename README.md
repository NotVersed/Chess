# Chess Engine

A fully playable chess application built in Java with a custom Swing GUI and a bitboard-based chess engine.

## Features
- Drag-and-drop piece movement with legal move highlighting
- Complete chess rule validation — en passant, castling, check/checkmate/stalemate detection
- Bitboard board representation using 64-bit integers and bitwise operations for fast move generation
- Minimax search algorithm with alpha-beta pruning
- Precomputed attack tables for O(1) piece move lookup
- Move encoding packed into 16-bit integers
- Configurable engine difficulty (Easy/Medium/Hard) via search depth
- Side selection — play as white or black

## Technical Highlights
- **Bitboards** — each piece type stored as a `long`, enabling O(1) board queries and fast position evaluation using `Long.bitCount`
- **Move encoding** — moves packed into a single `int` (source: bits 0-5, destination: bits 6-11, type: bits 12-13, promotion: bits 14-15)
- **Minimax + Alpha-Beta** — engine searches N moves ahead, pruning branches that can't influence the result
- **Make/Unmake** — engine applies and undoes moves during search without copying board state

## Built With
Java, Swing
