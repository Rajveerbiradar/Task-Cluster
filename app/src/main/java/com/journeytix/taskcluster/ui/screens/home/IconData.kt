package com.journeytix.taskcluster.ui.screens.home

/* Section-icon categories for the picker. Because icons are discovered at runtime
   from the installed drawables (hundreds of HugeIcons keys), categories are built
   by keyword-matching the clean key names rather than hand-listing every icon —
   each key can appear in more than one category, and anything unmatched lands in
   "Other". An "All" group is prepended so the full set stays browsable. */
data class IconCategory(val label: String, val keys: List<String>)

private data class IconRule(val label: String, val keywords: List<String>)

private val ICON_RULES = listOf(
    IconRule("Tech", listOf(
        "ai-", "brain", "binary", "code", "cpu", "api", "database", "server", "webhook",
        "git", "github", "computer", "terminal", "hard-drive", "firewall", "nano", "function",
        "command", "component", "windows", "drone", "robot", "android", "apple-company",
        "apple-01", "apple-vision", "instagram", "airbnb", "google", "laptop", "smart-phone",
        "printer", "cctv", "satellite", "battery", "plug", "container",
    )),
    IconRule("Nature", listOf(
        "tree", "leaf", "flower", "plant", "mountain", "sun", "moon", "cloud", "snow",
        "sakura", "tulip", "clover", "earth", "saturn", "comet", "droplet", "water", "sunrise",
        "sunset", "wave", "forest", "humidity", "temperature", "thermometer", "solar-system",
        "fire", "flame", "globe",
    )),
    IconRule("Food", listOf(
        "coffee", "beer", "cake", "cupcake", "pizza", "egg", "carrot", "salad", "dish",
        "cookie", "ice-cream", "milk", "chef", "kitchen", "knife", "spoon", "pot", "pan",
        "shellfish", "fish", "honey", "beans",
    )),
    IconRule("Sport & health", listOf(
        "dumbbell", "yoga", "swimming", "running", "workout", "football", "basketball",
        "baseball", "volleyball", "badminton", "golf", "fencing", "bicycle", "walking",
        "muscle", "whistle", "champion", "medal", "award", "racing-flag", "olympic", "laurel",
        "ranking", "weight-scale", "pill", "bandage", "dental", "tooth", "stethoscope",
        "lungs", "test-tube",
    )),
    IconRule("Travel", listOf(
        "airplane", "aircraft", "bus", "metro", "train", "motorbike", "anchor", "tent",
        "castle", "home", "door", "passport", "compass", "balloon", "zeppelin", "spaceship",
        "rocket", "map", "direction", "horse", "footprint",
    )),
    IconRule("Money & work", listOf(
        "dollar", "coin", "money", "wallet", "bank", "credit-card", "invoice", "briefcase",
        "certificate", "diploma", "chart", "presentation", "percent", "coupon", "piggy",
        "badge", "graduation", "content-writing", "stamp", "justice", "scale",
    )),
    IconRule("Symbols", listOf(
        "circle", "octagon", "pentagon", "diamond", "spades", "joker", "hashtag", "infinity",
        "not-equal", "crosshair", "target", "star", "heart", "crown", "gem", "flag", "label",
        "tag", "spark", "sparkle", "shapes", "key", "honour", "crown", "command",
    )),
    IconRule("Objects", listOf(
        "hammer", "tool", "ruler", "brush", "paint", "pen", "pencil", "lamp", "camera",
        "headphone", "mic", "guitar", "music", "book", "library", "file", "folder", "note",
        "clipboard", "bell", "gift", "backpack", "bed", "sofa", "chair", "bomb", "shield",
        "puzzle", "game", "joystick", "gamepad", "vynil", "film", "video", "image", "attachment",
        "mail", "megaphone", "bulb", "idea", "magic-wand", "quill",
    )),
)

/** Builds the picker's category list from the installed icon keys. */
fun buildIconCategories(allKeys: List<String>): List<IconCategory> {
    val result = mutableListOf(IconCategory("All", allKeys))
    val matched = mutableSetOf<String>()
    for (rule in ICON_RULES) {
        val keys = allKeys.filter { key -> rule.keywords.any { key.contains(it) } }
        if (keys.isNotEmpty()) {
            result.add(IconCategory(rule.label, keys))
            matched.addAll(keys)
        }
    }
    val other = allKeys.filter { it !in matched }
    if (other.isNotEmpty()) result.add(IconCategory("Other", other))
    return result
}
