# PlanetariumSX2 — prototype

A landscape Android companion UI prototype with a distinct Planetarium icon and a GitHub Actions APK build.

## Important limitation
This ZIP is **not a modified AetherSX2 emulator**. It is a starter companion UI prototype. It can display a game-file picker, but it does not execute BIN/CUE/ISO files, inject touchscreen input into AetherSX2, or include a PS2 emulation engine. AetherSX2's full application source is not available as a complete open-source project. Do not represent this APK as an emulator.

## Build with GitHub Actions
1. Create a new GitHub repository named `PlanetariumSX2`.
2. Upload the contents of this ZIP to the repository root.
3. Open **Actions** → **Build PlanetariumSX2 APK** → **Run workflow**.
4. When finished, open the workflow run and download artifact `PlanetariumSX2-debug`.
5. Extract the artifact ZIP and install the APK on your Android device.

## Termux upload
Extract this project in Termux, then:
```sh
cd ~/PlanetariumSX2
git init
git add .
git commit -m "Initial PlanetariumSX2 prototype"
git branch -M main
git remote add origin https://github.com/USERNAME/PlanetariumSX2.git
git push -u origin main
```
Replace `USERNAME` with your GitHub username. If the repository already exists, do not run `git init`; copy these files into that repository and commit the changes.

## Current prototype features
- Forced landscape orientation and immersive fullscreen.
- Custom vector launcher icon.
- Planetarium-themed dark UI.
- Game file picker placeholder.
- Decorative controller layout.

## Next engineering step
Implement a legally compatible emulator integration and verified controller-input bridge. The current visual controller is decorative and is not yet wired to an emulator.
