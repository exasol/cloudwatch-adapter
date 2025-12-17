#!/bin/bash
set -euo pipefail

versionLine=$(grep 'SemanticVersion' sam/template.yaml)
readonly versionLine

if [[ $versionLine == *"$1"* ]]; then # the * acts as wildcard --> it's a contains
  echo "Version in sam/template.yaml is valid"
  exit 0
else
  echo "Outdated version im sam/template.yaml. Please set SemanticVersion: $1"
  exit 1
fi
