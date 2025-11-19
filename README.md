# Hospital Management System

The purpose of this document is to serve as guide regarding formatting, style and naming standards / conventions to use when working on this project. While this may seem trivial, these minor details will help to provide clarity and understanding when in doubt.

---

## Git

#### Initial Setup

**Clone the repository** to your local machine in your desired path (traverse to it using *cd* in terminal)

```bash
git clone https://github.com/s25807/hospital-management-system.git
cd hospital-management-system
```

> **Info:** Once you open the project with Intellij - everything should be automatically loaded and runnable, if not let me know.

#### Implementing a new feature

**Create a new branch** to isolate your changes & features. Use a descriptive branch name:

```bash
git checkout -b feature.name-category
```

Where:
- Feature is the code you are working on
- Category is the status of the feature; **use:**
    - *fix* [You are fixing a bug]
    - *patch* [You forgot to add/implement something]
    - *update* [You are creating a new feature]
    - temp [You are temporarily introducing a feature -> to be removed later]

Example branch name:
```bash
git checkout -b report.system-update
```

> **Tip:** Avoid making changes on the main branch explicitly - you shouldn't have access but don't try it

#### Submitting Changes

1. **Fetch & Pull main**
    - Ensure you are on the newest main *after creating a new branch*

Example:

```bash
git checkout -b report.system-update
git fetch --all
git pull origin main
```

2. **Make your changes**
    - Make your edits or additions to the code base
    - Ensure your code is **formatted properly** and follows the standard style guides
    - Make sure it passed **all** tests (if applicable) for all new functionalities
    - Verify that everything works correctly before committing

3. **Commit your changes**
    - Use clear and descriptive commit titles and comments

Example
```bash
git status -u # Check all changes
git add README.md # For specific files
git add . # For all changed files
git commit -m "[10C] super.tax.rate.delete-fix" -m "I fixed the system error when deleting tax rates as super admin. One thing to note..."
git push origin super.tax.rate.delete-fix
```

>**Tip:** You can make the comments quick when committing, however, you should specify them further when making a **pull request**

4. **Verify updated main**
    - Check with *git fetch --all* if any new changes have been pulled into the *main* branch
    - If so, scan for any potential collisions

5. **Open a Pull Request (PR)**
    - Go to the main repository on *[GitHub](https://github.com/s25807/hospital-management-system)*
    - Click **"New Pull Request"** (under commit statistic and to the left of "Find a file") in your branch

    - Title should contain the **ticket number** as well as feature name
        - For e.g., **[10C] super.tax.rate.delete-fix**
        - Usually it'll be the same as what your ticket name is

    - Provide a detailed description of your changes and what they do
        - For e.g., *I fixed the system error when deleting tax rates as super admin. One thing to note...*
        - While you should always communicate complex changes with the team, it is **CRUCIAL** to provide a very **DETAILED** description of changes
        - Should contain: what they mean for the rest of the project; any concerns; implementation methods. This also serves for us to look back on in the future when we're wondering about the purpose the pull request

    - If no collisions are visible in your **pull request**, request code review
    - Else resolve it - if they are complex, contact lead & team
    - Submit the pull request for review
        - Assign 2 team members as reviewers - one of them should be the lead
        - Assign yourself as an assignee
        - Assign a label to the pull request

6. **Respond to review feedback**
    - Be responsive to any comments, questions or change requests from the team members
    - Push follow-up commits to the same branch; the PR will update automatically
    - Finally merge to main once everyone approves

## Coding

#### Java

1. **General Principles**
    - Use **PascalCase** for classes and enums
    - Use **camelCase** for variables, methods, and parameters
    - Use **UPPER_SNAKE_CASE** for constants
    - Use **kebab-case** for file and folder name when referring to *non-code* assets (e.g., templates, static files)

2. **Packages**
    - Always lowercase, **reverse-domain style**
    - Example: `main.java.models`

```java
public enum Status { ACTIVE, INACTIVE, PENDING }
```

3. **Methods & Variables**
    - Verbs for methods: `createUser()`, `findAll()`, `updateStatus()`
    - Boolean methods start with `is`, `has`, or `should`: `isActive()`, `hasAccess()`
    - Local variables and fields: descriptive, camelCase – `userRepository`, `bugReportList`

4. **Resources**
    - **Templates (Thymeleaf, etc.):** lowercase, words separated by hyphens, e.g. `bug-report-form.html`
    - **Static files:**
        - CSS/JS: `main.css`, `app.js`
        - Images: `logo.png`, `error-banner.jpg`