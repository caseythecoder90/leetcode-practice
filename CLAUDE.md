# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Development Commands

This is a Maven-based Java project. Common commands:

```bash
# Compile the project
mvn compile

# Run a specific Java class with main method
mvn exec:java -Dexec.mainClass="leetcode.pattern.problems.ProblemName.ClassName"

# Clean and compile
mvn clean compile
```

**Note**: There are no automated tests in this repository. Each solution class includes a `main` method with test cases that can be run individually.

## Repository Architecture

This is a **LeetCode interview preparation repository** organized by algorithmic patterns. The codebase follows a three-tier educational structure:

### Pattern Organization
```
src/main/java/leetcode/
├── arrays/
├── backtracking/
├── bitmanipulation/
├── dynamicprogramming/
├── graph/
├── linkedlist/
├── slidingwindow/
├── trees/
└── twopointers/
```

### Each Pattern Contains:
- **README.md**: Comprehensive pattern overview, techniques, and when to use
- **CheatSheet.md**: Quick reference templates and code snippets for interviews
- **problems/**: Individual LeetCode problems, each in its own directory
- **common/** or **practice/**: Shared utilities (when present)

### Problem Structure:
```
problems/ProblemName/
├── README.md         # Problem description, approach, complexity analysis
├── StudyGuide.md     # Detailed walkthrough with execution traces (optional)
└── Solution.java     # Implementation with main method and test cases
```

## Code Conventions

### Java Package Structure
- All problems: `leetcode.pattern.problems.ProblemName`
- Each problem gets its own package directory

### Standard Class Template
```java
public class ProblemName {
    public ReturnType solutionMethod(parameters) {
        // Implementation
    }
    
    public static void main(String[] args) {
        ProblemName solution = new ProblemName();
        // Test cases with expected outputs
    }
}
```

### Pattern-Specific Conventions

**Backtracking**: Uses consistent "Choose → Explore → Unchoose" template with standard parameters (`result`, `current`, `start`)

**Bit Manipulation**: Clean implementations leveraging XOR properties and bitwise operations

## Key Implementation Notes

- **Every solution must include a `main` method** with comprehensive test cases
- Use `new ArrayList<>(current)` when copying lists in backtracking
- Include inline comments explaining key algorithmic concepts
- Test cases should show expected outputs for verification
- Follow the existing naming conventions for parameters and variables

## File Creation Guidelines

When adding new problems:
1. Create directory: `src/main/java/leetcode/pattern/problems/ProblemName/`
2. Always include `README.md` with problem analysis
3. Implementation goes in `ProblemName.java` 
4. Add `StudyGuide.md` for complex problems requiring detailed walkthrough
5. Update the pattern's main README.md to reference the new problem

The repository prioritizes **educational value** and **systematic learning** over production code standards.