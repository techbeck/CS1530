# CS1530 Project - Team Caffeine

A Team Caffeine project assigned by [@laboon](http://www.github.com/laboon).

# Team Resources

  - [Google Drive](https://drive.google.com/drive/folders/0B4yNvwkqZ_goN0pxNGlCaFo1aFE)
  - [Slack Channel](https://teamkaffeine.slack.com)
  - [Team GitHub Page](https://github.com/TeamCaffeine)
  - [Trello Board](https://trello.com/team_caffeine)

# Branching and Pull Requests

For safety's concern, changes will not be made to the Team Caffeine `master`
branch until a merge req from a "feature branch" (e.g. `feature/feature-name`)
has been issued and that code been subject to a review.

To issue a change to the Team Caffeine project repository:

  1. Clone the TeamCaffeine repository.
    - Using [https](), `https://github.com/TeamCaffeine/cs1530.git`.
    - Using [ssh](), `git@github.com:TeamCaffeine/cs1530.git`.
  2. Create a new branch for your changes.
    - For a feature, `git checkout -b feature/feature-name`.
    - For a test or set of tests, `git checkout -b tests/test-suite-name`.
    - For a fix, `git checkout -b fix/feature-name` (corresponding with the feature being fixed).
  3. Make your changes and evaluate them.
    - Ensure it compiles with `gradle build`, then
    - Make sure it passes tests with `gradle test`, and finally
    - Make sure it runs with `gradle run`.
  4. Prepare your Git Commit. These steps are for one last _final_ commit,
  hopefully you've made a series of commits as you've been working.
    - See your list of changes with `git status`.
    - Add the files you've changed with `git add file1 folder2 file2 file3`
    - Make your commit with `git commit -m "Descriptive Message Here"
    - Push your commit to a new remote branch with `git push --set-upstream origin feature/feature-name`
  6. On the GitHub website, create a merge request (called a "pull request" for
  some reason) that compares your feature branch against the master branch.
  7. Others will review and comment on the code if neccessary. You can make new
  changes and commits-- whenever you push, the merge request will be updated.
  8. Once the code is validated _and_ verified, the team owner (currently
  [@jwarner112](http://www.github.com/jwarner112)) will merge into the repo's
  `master` branch.
