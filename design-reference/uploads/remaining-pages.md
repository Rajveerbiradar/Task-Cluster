# Remaining Pages — Todo List App

---

## 1. Settings

### 1.1 Appearance

- **Theme** — Light / Dark / System Default (radio selection)

### 1.2 Tasks

- **Default Priority** — Priority assigned to new tasks when none is selected
  - Options: None / Low / Medium / High
- **Default Sort Order** — How the main list is sorted by default
  - Options: Date Added / Due Date / Priority
- **Completed Tasks Behavior** — What happens to tasks after they're marked done
  - Auto-hide completed tasks after: Off / 1 day / 3 days / 7 days
  - "Clear All Completed" button — moves all completed tasks to trash at once (confirmation dialog required)

### 1.3 Notifications

- **Due Date Reminders** — Toggle ON / OFF
- **Default Reminder Time** — When the reminder fires relative to the due date
  - Options: At the time / 10 minutes before / 30 minutes before / 1 hour before / Morning of (9:00 AM)

### 1.4 Trash

- **Auto-Delete Trashed Items** — Permanently delete items in trash after a set period
  - Options: 7 days / 14 days / 30 days / Never

### 1.5 Data

- **Export Tasks** — Export all tasks as a `.csv` or `.json` file (share intent)
- **Import Tasks** — Import tasks from a `.csv` or `.json` file (file picker)

### 1.6 Legal

Links that open in-app or redirect to hosted web pages:

- Privacy Policy
- Terms of Service
- End User License Agreement (EULA)
- Data Deletion Policy

### 1.7 Danger Zone

- **Reset to Default Settings** — Resets all settings to factory defaults
  - Confirmation dialog: "This will reset all settings to their defaults. Your tasks will not be affected."
  - Actions: Cancel / Reset

---

## 2. Trash / Recently Deleted

### 2.1 Screen Layout

- **Top Bar** — Screen title: "Trash"
- **Info Banner** — Subtle text below the top bar: "Items are permanently deleted after [X] days" (value pulled from Settings > Trash > Auto-Delete period)
- **Trash List** — Each item shows:
  - Task name
  - Original due date (if one was set)
  - Deletion date
  - Days remaining before permanent deletion
- **Empty State** — When no items are in trash
  - Illustration or icon
  - Text: "Trash is empty"

### 2.2 Actions Per Item

- **Restore** — Moves the task back to the main list in its original state (swipe action or button)
- **Delete Permanently** — Removes the task forever
  - Confirmation dialog: "This task will be permanently deleted. This action cannot be undone."
  - Actions: Cancel / Delete

### 2.3 Bulk Actions

- **Empty Trash** — Button at the top or bottom of the list
  - Confirmation dialog: "All [X] items in trash will be permanently deleted. This cannot be undone."
  - Actions: Cancel / Empty Trash
- **Restore All** — Restores every trashed item back to the main list
  - Confirmation dialog: "All [X] items will be restored to your task list."
  - Actions: Cancel / Restore All

---

## 3. About

### 3.1 App Identity

- App icon / logo
- App name
- Version number (e.g., v1.0.0) and build number (e.g., Build 12)
- One-liner tagline or description (e.g., "A simple, focused task manager.")

### 3.2 Creator

- "Made by [Your Name / Brand Name]"
- Link to your website or portfolio (optional)
- Links to your social profiles (optional)

### 3.3 Actions

- **Rate This App** — Deep link to the app's Play Store listing for review
- **Share This App** — Opens Android share sheet with Play Store link and a short message (e.g., "Check out [App Name] — a simple todo list app. [Play Store Link]")
- **Send Feedback** — Opens email compose intent
  - To: your support email address
  - Subject: pre-filled with "[App Name] Feedback — v[version]"
  - Body: pre-filled with device info (model, Android version, app version) for debugging context

### 3.4 Legal Links

Same links as in Settings, duplicated here for easy access:

- Privacy Policy
- Terms of Service
- End User License Agreement (EULA)
- Data Deletion Policy

### 3.5 Open Source Licenses

- Standard Android open source licenses screen listing all third-party libraries used and their licenses

---

## 4. Legal Pages

All legal pages must have the following at the top:

- Document title
- Effective date
- Last updated date

And the following at the bottom:

- Contact email for questions

Legal pages should be hosted on your website (e.g., AmchiLocal.com or Journeytix.com) and loaded in-app via a WebView or linked out to the browser. This lets you update them without pushing an app update.

---

### 4.1 Privacy Policy

> Required by Google Play Store. App will be rejected without one.

#### Sections to include:

**a) Introduction**
- Name of the app and the developer/entity behind it
- What this policy covers

**b) Information We Collect**
- Data the user provides directly (task content, task titles, due dates, priority levels — all stored locally)
- Data collected automatically (device model, OS version, crash logs — if using any analytics/crash reporting SDK)
- Data we do NOT collect — explicitly state if you do not collect personal information, location, contacts, etc.

**c) How We Use Your Information**
- To provide and improve the app experience
- To diagnose crashes and bugs (if using crash reporting)
- We do NOT sell, share, or transfer user data to third parties for advertising or marketing

**d) Data Storage and Security**
- All task data is stored locally on the user's device
- No server-side storage or cloud sync (update this section if you add cloud features later)
- Data is protected by the device's own security mechanisms (lock screen, encryption)

**e) Third-Party Services**
- List every third-party SDK integrated and link to their privacy policies
- Common ones: Firebase Analytics, Firebase Crashlytics, Google AdMob (if ads are added later)
- If none are used, state: "This app does not use any third-party analytics or tracking services."

**f) Children's Privacy**
- The app is not directed at children under the age of 13
- You do not knowingly collect personal information from children under 13
- If you become aware of such collection, you will delete it immediately (COPPA compliance)

**g) User Rights and Data Deletion**
- Users can delete all their data by clearing the app's data from Android Settings or by uninstalling the app
- No account creation is required, so no account deletion request is needed (update if accounts are added later)

**h) Changes to This Policy**
- You reserve the right to update this policy
- Users will be notified of significant changes via an in-app notice or update notes
- Continued use after changes constitutes acceptance

**i) Contact**
- Email address for privacy-related questions

---

### 4.2 Terms of Service / Terms & Conditions

#### Sections to include:

**a) Acceptance of Terms**
- By downloading, installing, or using the app, the user agrees to these terms
- If they do not agree, they must stop using and uninstall the app

**b) Description of Service**
- The app is a personal task management tool
- It is provided for individual, non-commercial use

**c) User Responsibilities**
- Users are responsible for their own data and maintaining backups
- Users must not use the app for any illegal or unauthorized purpose
- Users must not attempt to reverse-engineer, decompile, or modify the app

**d) Intellectual Property**
- The app, including its design, code, graphics, and branding, is the intellectual property of [Your Name / Entity]
- Users are granted a limited, non-exclusive, non-transferable license to use the app for personal purposes
- No rights are transferred to the user beyond what is explicitly stated

**e) Disclaimer of Warranties**
- The app is provided "as is" and "as available" without warranties of any kind
- No guarantee of uninterrupted, error-free, or secure operation
- No guarantee that data will not be lost

**f) Limitation of Liability**
- You are not liable for any direct, indirect, incidental, or consequential damages arising from the use of the app
- This includes but is not limited to: lost data, missed deadlines, or any reliance on the app's reminders or notifications

**g) Termination**
- You reserve the right to discontinue the app at any time without notice
- Users can terminate their use by uninstalling the app

**h) Changes to Terms**
- You reserve the right to modify these terms at any time
- Continued use after modifications constitutes acceptance

**i) Governing Law**
- These terms are governed by the laws of India
- Any disputes will be subject to the exclusive jurisdiction of the courts in Mumbai, Maharashtra

**j) Contact**
- Email address for questions about these terms

---

### 4.3 End User License Agreement (EULA)

#### Sections to include:

**a) License Grant**
- You grant the user a revocable, non-exclusive, non-transferable, limited license to install and use the app on devices they own or control, strictly in accordance with these terms

**b) License Restrictions**
- Users may NOT:
  - Copy, modify, or distribute the app
  - Reverse-engineer, decompile, or disassemble the app
  - Sell, rent, lease, or sublicense the app
  - Use the app for any commercial purpose without written permission
  - Remove or alter any proprietary notices or labels

**c) Ownership**
- The app is licensed, not sold
- All rights, title, and interest in the app remain with [Your Name / Entity]
- This agreement does not transfer any ownership rights

**d) Updates**
- You may release updates, patches, or new versions at your discretion
- Some updates may be required for continued use
- This agreement applies to all updates unless a separate agreement is provided

**e) Termination**
- This license is effective until terminated
- It terminates automatically if the user violates any of the terms
- Upon termination, the user must uninstall and destroy all copies of the app

**f) Disclaimer**
- Same disclaimer of warranties as in Terms of Service

**g) Limitation of Liability**
- Same limitation of liability as in Terms of Service

**h) Governing Law**
- Same governing law clause as in Terms of Service

---

### 4.4 Data Deletion Policy

> Google Play may require a data deletion page/URL during app submission.

#### Sections to include:

**a) What Data the App Stores**
- Task names, descriptions, due dates, priority levels, completion status
- App settings and preferences
- All data is stored locally on the user's device

**b) How to Delete Your Data**

- **Delete individual tasks** — Swipe to delete or use the delete option within the app. Deleted tasks move to Trash and are permanently removed after [X] days (configurable in Settings).
- **Empty Trash** — Go to Trash and tap "Empty Trash" to permanently delete all trashed items immediately.
- **Clear all app data** — Go to Android Settings > Apps > [App Name] > Storage > Clear Data. This removes all tasks, settings, and preferences.
- **Uninstall the app** — Uninstalling the app removes all locally stored data from the device.

**c) What Happens After Deletion**
- Deleted data cannot be recovered
- Since no data is stored on external servers, deletion is immediate and complete
- No backups are retained by the developer

**d) Third-Party Data**
- If the app uses third-party services (e.g., Firebase Crashlytics), crash logs may be retained by those services according to their own data retention policies
- Links to third-party data deletion procedures (if applicable)

**e) Contact**
- Email address for data deletion requests or questions

---

## Implementation Notes

### Hosting Legal Pages
- Host all four legal documents as web pages on your domain (e.g., `yourapp.com/privacy`, `yourapp.com/terms`, `yourapp.com/eula`, `yourapp.com/data-deletion`)
- Load them in-app using a WebView or open them in the system browser
- This allows you to update legal text without shipping an app update
- Google Play requires a Privacy Policy URL during app submission — use the hosted URL

### Confirmation Dialogs
Every destructive action across all screens must have a confirmation dialog:
- Clear all completed tasks
- Delete permanently (single item)
- Empty trash
- Restore all
- Reset settings to default

### Navigation
- Three-dot overflow menu on the main screen contains: Settings, Trash, About
- Settings screen links to all four legal pages at the bottom
- About screen also links to all four legal pages
- Legal pages open in a WebView screen or external browser
