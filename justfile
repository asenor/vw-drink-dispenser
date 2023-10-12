set dotenv-load := true

# List available commands
default:
  just --list --unsorted


#------ GRADLE

common_gradle_args := "--warning-mode all"

# Execute gradlew task
gradle task:
  ./gradlew {{task}} {{common_gradle_args}}

# Build the project
build: clean
  ./gradlew build

# Clean build files
clean:
  ./gradlew clean {{common_gradle_args}}


#------ TEST

# Run all tests
test: test-unit test-integration test-architecture

# Run unit test/s
test-unit *FILE:
  #!/usr/bin/env sh
  if [ -z "{{FILE}}" ]; then
    ./gradlew test {{common_gradle_args}}
  else
    ./gradlew test {{common_gradle_args}} --tests {{FILE}}
  fi

# Run integration tests
test-integration:
  ./gradlew testIntegration {{common_gradle_args}}

# Run architecture tests
test-architecture:
  ./gradlew testArchitecture {{common_gradle_args}}

# Generate code coverage
test-coverage:
  ./gradlew build jacocoTestReport
  @echo "\nYou can see the results here: {{justfile_directory()}}/build/jacocoHtml/index.html"
