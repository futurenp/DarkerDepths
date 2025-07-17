@echo off
setlocal enabledelayedexpansion

:: ============================================================================
::  Batch Script to Replace Text and Rename Files
::
::  Description:
::  This script finds all files in the current directory with "limestone"
::  in their name. For each file, it replaces the text "limestone" with
::  "duskrock" inside the file, and then renames the file itself.
::
::  Instructions:
::  1. Place this .bat file in the target directory.
::  2. !! BACK UP YOUR FILES BEFORE RUNNING !! Changes are irreversible.
::  3. Double-click the file to execute it.
:: ============================================================================

set "searchTerm=limestone"
set "replaceTerm=duskrock"

echo Starting replacement and rename for files containing '%searchTerm%'...
echo.

:: Find all files containing the search term in their filename.
for %%f in (*%searchTerm%*.*) do (
    echo Processing file: "%%f"

    :: 1. Replace content inside the file
    echo   - Replacing text content...
    powershell -Command "(Get-Content '%%f' -Raw) -replace '%searchTerm%', '%replaceTerm%' | Set-Content '%%f'"

    :: 2. Rename the file
    set "originalName=%%~nxf"
    set "newName=!originalName:%searchTerm%=%replaceTerm%!"
    echo   - Renaming to "!newName!"
    ren "%%f" "!newName!"
    echo.
)

echo.
echo =================================
echo  Process complete.
echo =================================
echo.

pause
