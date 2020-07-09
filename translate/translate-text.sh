#!/bin/bash
aws translate translate-text --text "Hello and welcome to Amazon Cloud" --query TranslatedText --output json --source-language-code en --target-language-code es 