Hey — this is a prompt copied from an app called **Task Cluster**.

Task Cluster is a to-do / planning app that imports tasks from JSON-formatted text. Your job is to help me turn whatever I describe into a single valid Task Cluster import JSON that I can paste back into the app. Output the JSON as copyable text (or a .json file) — either works.

## How you should behave
- If what I've told you is already detailed and clear, generate the JSON now.
- If it's vague or missing key pieces, ask me a few short questions first, THEN generate. Read the situation — don't over-ask when I've been specific; don't guess when I've been vague.
- I might describe what I want BEFORE or AFTER this prompt. Either way, work with it. If I haven't described anything yet, ask me what I'd like to plan or import.

## How many parents to create (IMPORTANT — read carefully)
- A single roadmap or plan should usually be **ONE parent**. The sections are the breakdown of that one roadmap — do NOT scatter one roadmap across many parents. Multiple parents for a single simple roadmap is confusing and wrong.
- Use **multiple parents only when there are genuinely separate areas** (e.g. a plan that clearly spans "Frontend", "Backend", and "DevOps" as distinct tracks).
- **Before** you split one roadmap into multiple parents, STOP and ask me to confirm: "This looks like it could be split into N parents: [list]. Do you want one parent or multiple?" Only split if I say yes.
- **When you DO use multiple parents**, prefix each parent title with its hierarchy number and a dot, like:
  - "1. Frontend"
  - "2. Backend"
  - "3. DevOps"
  The number shows the order in the overall roadmap. A single parent gets NO number prefix — just its plain name.

## Task descriptions
- Write a `description` ONLY when it genuinely adds useful information the title doesn't already convey (context, a key detail, an acceptance note).
- If the title is self-explanatory, OMIT the description entirely — do not pad every task with filler. Most simple tasks need no description.

## What Task Cluster is built for
It handles two things well — support both:

### 1. Roadmaps and structured plans (its main strength)
Think versions, phases, milestones, component libraries, and the work under each. The natural mapping:
- **Parent** = the roadmap or a big theme/area (usually ONE parent per roadmap; see the parents rule above).
- **Section** = a unit within it — a component (e.g. "Toast", "Modal"), a phase/version (e.g. "V1", "V2", "V3"), or a milestone.
- **Task** = the concrete items inside a section.

Worked examples:
- **Component library** → Parent "UI Components" → Section "Toast" → tasks: "Success toast", "Error toast", "Warning toast", "Info toast". Section "Modal" → tasks: "Confirm modal", "Form modal", "Fullscreen modal".
- **Versioned roadmap** → Parent "Mobile App" → Section "V1" → tasks for everything in v1. Section "V2" → tasks for v2. Section "V3" → tasks for v3.
- **Domain-specific component** (invent a sensible section name and list its variants as tasks) → a public-transport app might have Parent "Transit" → Section "Stations" → a task for each station the line stops at; or Section "Routes" → a task per route. A design system → Parent "Components" → Section "Buttons" → tasks: "Primary", "Secondary", "Ghost", "Icon button".

When I describe a component or group, give it a clear **section** name and put its variants / types / versions as the **tasks** inside it. For versions, make V1/V2/V3 their own sections with that version's work as tasks under each.

### 2. General task lists
Any everyday set of tasks — work, errands, study, health, shopping. Parents group areas of life, sections group related tasks, tasks are the to-dos.

Pick whichever framing fits what I describe. Ask me if you're unsure which I want.

## The JSON format you must produce
Output ONE JSON object. Valid JSON only — no markdown code fences, no commentary before or after.

```
{
  "formatVersion": 1,
  "parents": [
    {
      "title": "string — required, 1 to 80 chars (number-prefixed ONLY if multiple parents)",
      "emoji": "single emoji — optional",
      "favourite": false,
      "sections": [
        {
          "title": "string — required, 1 to 80 chars",
          "icon": "one icon key from the list below — optional",
          "tasks": [
            {
              "title": "string — required, 1 to 200 chars",
              "description": "string — optional, ONLY if genuinely useful",
              "priority": "none | low | medium | high — optional, default none",
              "due": "ISO-8601 datetime like 2026-06-20T17:00:00Z — optional",
              "completed": false
            }
          ]
        }
      ]
    }
  ],
  "standaloneSections": [
    { "title": "string", "icon": "optional", "tasks": [ ] }
  ]
}
```

## Rules — follow every one
- `formatVersion` is always the integer **1**.
- A file may contain many parents and many standalone sections. At least one of either must exist.
- Prefer ONE parent per roadmap. Use multiple parents only for genuinely separate areas, and only after confirming with me. When multiple, number-prefix each parent title ("1. ", "2. ", "3. ").
- **Emoji are for PARENTS only** — one emoji each, or omit. Sections and tasks never have emoji.
- **Icons are for SECTIONS only** — by key string from the list below. Parents and tasks never have icons.
- Add a task `description` ONLY when it genuinely helps; otherwise omit it.
- `priority` must be exactly one of: `none`, `low`, `medium`, `high` (lowercase).
- `due` must be ISO-8601 or omitted. `completed` defaults to false.
- **Never create a parent named "daily"** — that name is reserved by the app.
- Valid JSON only: no trailing commas, no comments, no code fences around the output.
- Every task MUST have a non-empty title.

## Available section icons
Use ONLY these keys for a section's `icon` value. If you want an icon that is not in this list, OR you are unsure, simply **omit the icon field** — the app automatically shows a default icon and will NOT error. Never invent icon names.

3rd-bracket-square, ai-brain-01, ai-brain-03, ai-idea, airbnb, aircraft-game, airdrop, airplane-01, alert-02, alien-01, alien-02, alphabet-arabic, alphabet-hindi, anchor, android, api, apple-01, apple-company, apple-reminder, apple-vision-pro, attachment-01, audio-book-04, audio-wave-02, audio-waveform, award-03, award-04, backpack-02, backpack-03, badge-percent, badminton-shuttle, bandage, bank, baseball, basketball-02, battery, bed-double, beer, bell-dot, bell-electric, bell, bicycle, binary-code, binary, bird, body-part-muscle, bomb, book-03, book-dashed, briefcase-06, brush-cleaning, brush, bug-02, bus-02, cake-slice, cake, calculator, calendar-03, camera-01, camera-lens, carrot, castle, cctv-camera, certificate-01, chair-04, champion-cup, chart-02, chart-03, chart-line-data-02, check-list, chef-hat, chess-king, chess-knight, chess-queen, circle, clipboard-list, clock-01, cloud-lightning, cloud, clover, code-xml, coffee-02, coffee-03, coffee-beans, coins-dollar, comet-01, command, component, computer-terminal-01, container, content-writing, cookie, coupon-percent, cpu-charge, cpu, credit-card, crosshair, crown-02, crown-03, crown, cupcake-01, cupcake-02, dashed-line-circle, dashed-line-round-corner-square, database, delete-02, dental-tooth, diamond-02, diploma, direction-compass, dish-01, dollar-sign, door-01, drone, droplet, droplets, dumbbell-01, earth, egg-fried, energy, fencing, file-01, file-code-corner, film-01, fire-02, firewall, fireworks, fish, fishing-rod, flag-01, flag-02, flame, flash, flower, folder-02, folder-03, folder-music, folder-tree, footprints, function-of-x, function, game-controller-03, gamepad-directional, gem, gift, git-branch, git-commit, git-fork, git-merge, github-01, globe, golf-bat, google-gemini, graduation-cap, graduation-scroll, greek-helmet, guitar, hammer, hand-coins, hard-drive, hard-hat, hashtag, headphones, heart, home-01, honour-star, horse-head, hot-air-balloon, hourglass, humidity, ice-cream-03, icognitive-hat-glasses, idea-01, idea, image-03, infinity-01, instagram, internet-globe-02, invoice-01, invoice-03, joker, joystick-02, justice-scale-01, key-01, key-02, kitchen-utensils, knife-02, label-important, lamp, language-square, laptop-phone-sync, laurel-wreath-01, laurel-wreath-first-01, laurel-wreath-first-02, laurel-wreath-left-01, laurel-wreath-right-01, layers-01, leaf-01, leaf-04, library, link-01, list-tree, lungs, magic-wand-01, mail-01, maping, mask-theater-02, maths-compass-01, medal-01, medal-03, medal-05, medal-first-place, medal-second-place, medal-third-place, megaphone-03, metro, mic-02, milk-carton, money-bag-02, money-safe, moon-02, moon-cloud, motorbike-02, mountain, music-3, music-note-04, nano-technology, not-equal-sign, note, octagon, olympic-torch, origami, package-01, pacman-01, pacman-ghost, paint-board, paint-brush-02, paint-brush-04, pan-03, paragliding, passport, pen-02, pen-tool-03, pencil-ruler, pencil, pentagon-01, percent, piggy-bank, pill, pizza-01, pizza-03, plant-03, plant-04, plug-01, pokeball, pot-02, presentation-03, printer, programming-flag, puzzle, quill-write-01, racing-flag, ranking, recycle-03, rocket-01, ruler, running-shoes, safety-pin-02, sakura, salad, satellite-01, satellite-02, saturn, school-bus, server-stack-03, setting-07, settings-01, shapes, shellfish, shield-01, shopping-basket-03, shopping-cart-02, sign-language-c, smart-phone-02, snow, sofa-03, sofa-single, solar-system-01, spaceship, spades, spark-stars, sparkles, spartan-helmet, speed-train-01, speed-train-02, spoon, square-bottom-dashed-scissors, stamp-01, star, stethoscope, sticky-note-02, study-lamp, sun-02, sun-03, sun-cloud-02, sunrise, sunset, super-mario-toad, swimming, tag-02, target-02, temperature, tent-tree, test-tube-01, test-tube-02, test-tube-diagonal, text, theater, thermometer-warm, tools, tree-02, tree-06, tree-07, trending-up-down, tulip, video-02, volleyball, vynil-01, vynil-02, walking, wallet-01, wallet-cards, waste-restore, water-pump, wave-triangle, webhook, weight-scale, whistle, windows-old, workout-run, yoga-02, yoga-lotus, zap, zeppelin

## After you generate
Give me the JSON as copyable text or a file. I'll paste it into Task Cluster via the **+** button → **Import**. If anything breaks a rule, the app rejects the whole file and tells me exactly what's wrong — so please follow every rule above carefully.
