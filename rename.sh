#!/usr/bin/env bash
set -euo pipefail

# Usage: ./rename.sh com.mycompany.myapp MyApp com.mycompany.myapp https://api.example.com
NEW_PACKAGE=${1:?'Provide new package, e.g., com.mycompany.myapp'}
NEW_APP_NAME=${2:-MyApp}
NEW_BUNDLE_ID=${3:-$NEW_PACKAGE}
NEW_BASE_URL=${4:-https://jsonplaceholder.typicode.com}

OLD_PACKAGE="com.example.samplekmpproject"
OLD_APP_NAME="SampleKmpProject"
OLD_BUNDLE_ID="$OLD_PACKAGE"

# sed cross-platform inline flag
SED_I=("-i")
if [[ "$(uname)" == "Darwin" ]]; then SED_I=("-i" ""); fi

# 1) Replace app name and base URL in common text files
find . -type f \( -name "*.kt" -o -name "*.kts" -o -name "*.swift" -o -name "*.plist" -o -name "*.md" -o -name "*.gradle" -o -name "*.yml" -o -name "*.yaml" \) \
  -not -path "*/.git/*" \
  -exec sed "${SED_I[@]}" "s/${OLD_APP_NAME//\//\/}/${NEW_APP_NAME//\//\/}/g" {} +

# Replace BASE_URL if present (jsonplaceholder default) in Kotlin sources and Gradle Kotlin scripts (BuildKonfig)
find . -type f \( -name "*.kt" -o -name "*.kts" \) -not -path "*/.git/*" \
  -exec sed "${SED_I[@]}" "s#https://jsonplaceholder\\.typicode\\.com#${NEW_BASE_URL//#/\\#}#g" {} + || true

# 2) Replace package references in source and configs
find . -type f \( -name "*.kt" -o -name "*.kts" -o -name "*.swift" -o -name "*.plist" \) \
  -not -path "*/.git/*" \
  -exec sed "${SED_I[@]}" "s/${OLD_PACKAGE//./\\.}/${NEW_PACKAGE//./\\.}/g" {} +

# 3) Move Kotlin package directories under all src/*/kotlin roots
move_package_dir() {
  local SRC_DIR="$1"
  local OLD_PATH="${OLD_PACKAGE//./\/}"
  local NEW_PATH="${NEW_PACKAGE//./\/}"
  if [ -d "$SRC_DIR/$OLD_PATH" ]; then
    mkdir -p "$SRC_DIR/$NEW_PATH"
    rsync -a --remove-source-files "$SRC_DIR/$OLD_PATH/" "$SRC_DIR/$NEW_PATH/" || true
    find "$SRC_DIR/$OLD_PATH" -type d -empty -delete || true
  fi
}

while IFS= read -r -d '' dir; do
  move_package_dir "$dir"
done < <(find . -path "*/src/*/kotlin" -type d -print0)

# 4) iOS bundle id adjustments (project and plist)
find iosApp -type f \( -name "project.pbxproj" -o -name "*.plist" \) \
  -exec sed "${SED_I[@]}" "s/${OLD_BUNDLE_ID//./\\.}/${NEW_BUNDLE_ID//./\\.}/g" {} + || true

echo "Rename complete. Sync Gradle and clean build. You may need to set Team in Xcode."
