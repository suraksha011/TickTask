# TickTask — Green, Todoist-Inspired UI (Spring Boot + Security)

Same Spring Boot + Spring Security + H2 backend as before, with a redesigned frontend inspired by Todoist: a left sidebar, clean task rows, and an interactive "Add Task" modal.

## What's new in this version
- **Remember Me** — check the box on login to stay signed in for 14 days via a persistent cookie
- **Session timeout** — inactive sessions expire after 30 minutes; expired sessions redirect to login with a clear message
- **Single active session** — logging in elsewhere invalidates the older session
- **Fully responsive** — sidebar becomes a slide-out drawer on tablets/phones, task rows and the add-task modal reflow for small screens

## What's new in this version (frontend redesign, still applies)
- **Sidebar navigation** — All Tasks / Active / Completed, collapsible with the ☰ button
- **Green color palette** throughout
- **Interactive "Add Task" modal** — opens via the sidebar button, the top-right "+ Add" button, or by pressing **Q** anywhere on the page (closes with Escape or clicking outside)
- **Hover interactions** — task rows highlight on hover, delete button appears only on hover, animated checkbox fill on completion
- Login/Register pages restyled to match

## Tech Stack (unchanged)
- Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA, H2, Thymeleaf

---

## Setup in VS Code

```bash
cd task-manager-web-v3
mvn spring-boot:run
```
Then open: **http://localhost:8080**

Register an account, log in, and try:
- Clicking **"Add task"** in the sidebar or the **"+ Add"** button top-right
- Pressing **Q** to quick-open the add-task modal
- Hovering over a task row to reveal the delete (✕) button
- Clicking the sidebar collapse icon (☰) next to the project title

---

## Project Structure (changed files)
```
src/main/resources/
├── templates/
│   ├── login.html       # restyled
│   ├── register.html    # restyled
│   └── index.html       # full redesign: sidebar + task list + modal
└── static/
    ├── css/green-theme.css  # new green design system
    └── js/app.js             # modal open/close, sidebar toggle, "Q" shortcut
```

All backend Java classes (controllers, security, entities, repositories) are unchanged from the previous version — only the frontend changed.

## Resume bullet idea
*"Designed and built an interactive, Todoist-inspired task management UI in Spring Boot + Thymeleaf, featuring a collapsible sidebar, modal-based task creation, and keyboard shortcuts, on top of a secured multi-user backend."*
