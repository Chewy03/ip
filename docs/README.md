# JimmyTimmy User Guide

<img alt="image" src="https://github.com/user-attachments/assets/b63bfcbb-014a-4f7c-ba32-ea5fe3ddc2a2" />

JimmyTimmy is a todo shopping cart chatbot designed to help you manage tasks effortlessly. With an intuitive user interface and a responsive, friendly personality, it makes organizing your day easier and more enjoyable. Whether it’s adding, marking, or undoing tasks, JimmyTimmy keeps track of your to-dos with precision and efficiency.
## Adding deadlines

deadline (item) /by (yyyy-MM-dd HH:mm)
Adds a grocery item with an expiry date.

Example: `deadline Submit assignment /by 2025-09-20 23:59`

```
I've added this item:
🕗[] Submit assignment (by: Sep 20 2025 23:59)
Now you have 5 items in your cart

```

## Feature: Task Management Made Simple

JimmyTimmy allows you to efficiently manage your tasks and grocery items with a few simple commands:

- Add tasks: Quickly add new items to your list using todo, deadline, or event commands.

- Delete tasks: Remove items that are no longer needed using the delete command.

- Mark tasks: Keep track of completed items by marking them as done using mark and unmark them if needed.

- View your list: See all tasks in an organized, readable format with the list command.

Example:

```
todo Buy milk
deadline Submit report /by 2025-09-20 17:00
mark 1
list
```

### Outcome:
All tasks are neatly tracked, with clear indicators for completed and pending items, so you always know the status of your cart.

## Feature: Undo & Redo Functionality

JimmyTimmy keeps a history of your actions, allowing you to undo or redo commands effortlessly.

- Undo: Reverse the last action, whether it was adding, deleting, or marking a task.

- Redo: Restore the most recently undone action, giving you flexibility in managing mistakes or changes.

Example:

```
delete 2
undo
redo
```

### Outcome:

- delete 2 removes a task.
- undo restores it immediately.
- redo removes it again.

This ensures that you can experiment, correct errors, or adjust your list without worrying about losing data.
