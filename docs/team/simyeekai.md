---
  layout: default.md
  title: "simyeekai's Project Portfolio Page"
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo/redo previous commands.
    * What it does: Allows users to list all persons who have a matching time slot. When the user inputs a specific date and time range, the command filters and displays only those persons whose stored time slot corresponds exactly to the specified time period.
    * Justification: This feature improves the product’s usability by enabling users to quickly identify who is available or scheduled during a particular time. It eliminates the need to manually check each person’s entry and supports efficient coordination and planning.
    * Highlights: This enhancement involved extending the existing command system to handle new parameters (date and time ranges) and required modifications to the Model and Predicate components to support time-based filtering. Careful consideration was given to parsing, validation, and comparison of time formats to ensure consistent and accurate results. The implementation also integrates smoothly with the existing find command structure for scalability and future extensibility.
    * Credits: The idea and implementation are original. Standard Java date/time parsing (java.time.LocalDateTime) and comparison utilities were used for handling time slot matching.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

* **Code contributed**: [RepoSense link]()

* **Project management**:
    * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
    * Updated the GUI color scheme (Pull requests [\#33](), [\#34]())
    * Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear`, `exit`: [\#74]()
    * Developer Guide:
        * Added implementation details of the `delete` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* **Tools**:
    * Integrated a third party library (Natty) to the project ([\#42]())
    * Integrated a new Github plugin (CircleCI) to the team repo

* _{you can add/remove categories in the list above}_
