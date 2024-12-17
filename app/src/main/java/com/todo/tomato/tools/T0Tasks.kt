package com.todo.tomato.tools

import com.todo.tomato.R


val RandomTasks = arrayListOf(
    "Work", "Study", "Health", "Social", "Family", "Entertainment"
)

fun String.getRandomTask(): String {
    return when (this) {
        "Work" -> workTasks.random()
        "Study" -> studyTasks.random()
        "Health" -> healthTasks.random()
        "Social" -> socialTasks.random()
        "Family" -> familyTasks.random()
        "Entertainment" -> musicTasks.random()
        else -> workTasks.random()
    }
}


fun String.twoBack(): Int {
    return when (this) {
        "Work" -> R.drawable.t17
        "Study" -> R.drawable.t18
        "Health" -> R.drawable.t19
        "Social" -> R.drawable.t20
        "Family" -> R.drawable.t21
        "Emergency" -> R.drawable.t22
        "Entertainment" -> R.drawable.t23
        "Personal" -> R.drawable.t24
        else -> R.drawable.t25
    }
}

val workTasks = arrayListOf(
    "Organize your workspace by decluttering unnecessary items and files.",
    "Write a 5-minute workday summary and share it with a colleague.",
    "Spend 30 minutes learning about a business area you've never explored.",
    "Exchange ideas with a colleague on how to improve team collaboration.",
    "Propose a new idea or suggestion at today’s meeting.",
    "Design a simple logo or slogan for your team or company.",
    "Spend 15 minutes cleaning up and optimizing your email inbox.",
    "Record all the tasks you completed today and reflect on them.",
    "Plan your day using a new method, such as a mind map.",
    "Hold a brief phone call encouraging team members to share highlights of the day.",
    "Pick a management book from your shelf and read one chapter.",
    "Write a blog post or internal news article about your recent work.",
    "Prepare a short training document for colleagues on a skill you're good at.",
    "Find and install a new productivity tool, and give it a try.",
    "Create a to-do list for your goals for the next week.",
    "Tackle your least favorite task and complete it within one hour.",
    "Organize a 5-minute stand-up meeting to quickly discuss the day's goals.",
    "Create a list titled \"Things I Learned Today\" and share it with your team.",
    "Reach out to a colleague you haven't worked with in a while to discuss potential collaboration.",
    "Set a \"no-interruption\" time slot to focus on completing a critical task.",
)

val studyTasks = arrayListOf(
    "Read an article on a topic you've never studied before.",
    "Watch a 10-minute educational video on YouTube or another platform.",
    "Spend 15 minutes learning a new word in a foreign language.",
    "Take an online quiz to test your knowledge in a subject you're interested in.",
    "Pick a book from your reading list and read one chapter.",
    "Write a brief summary of something new you learned today.",
    "Listen to a podcast on a subject you want to explore further.",
    "Enroll in a free online course on a topic that fascinates you.",
    "Research a famous historical figure and learn about their achievements.",
    "Create flashcards to memorize key concepts from a recent lesson.",
    "Teach someone else something you learned recently.",
    "Watch a TED Talk on a subject you’re curious about.",
    "Set a timer for 30 minutes and study a topic that challenges you.",
    "Write a list of questions about a subject you want to understand better.",
    "Join an online discussion or forum related to your field of interest.",
    "Practice a new skill for 15 minutes, such as drawing, coding, or playing an instrument.",
    "Attend a virtual seminar or webinar on a relevant topic.",
    "Read the Wikipedia page of a concept you've always been curious about.",
    "Experiment with a new learning technique, like mind mapping or spaced repetition.",
    "Set a small goal to master a specific topic by the end of the week.",
)

val healthTasks = arrayListOf(
    "Take a 10-minute walk outside to get some fresh air. ",
    "Drink a glass of water first thing in the morning.",
    "Do a 5-minute stretching routine to start your day.",
    "Prepare a healthy snack instead of reaching for junk food.",
    "Try a new fruit or vegetable that you’ve never eaten before.",
    "Take a few deep breaths to relax and reduce stress.",
    "Do 10 push-ups or sit-ups to get your blood flowing.",
    "Spend 15 minutes meditating or practicing mindfulness.",
    "Replace one sugary drink with water or herbal tea.",
    "Get up from your desk and move around for 5 minutes every hour.",
    "Write down three things you’re grateful for today to boost your mental well-being.",
    "Plan and cook a healthy meal for yourself or your family.",
    "Set a timer for 20 minutes and do a quick home workout.",
    "Take a short break to look away from screens and rest your eyes.",
    "Try a new form of exercise, like yoga, dancing, or biking.",
    "Set a bedtime reminder to ensure you get enough sleep tonight.",
    "Practice good posture by sitting up straight while working.",
    "Replace one unhealthy snack with a piece of fruit or some nuts.",
    "Schedule a routine health check-up or dental appointment.",
    "Take a moment to check in with your body and notice how you’re feeling.",
)

val socialTasks = arrayListOf(
    "Reach out to a friend or family member you haven’t spoken to in a while.",
    "Compliment someone sincerely today, either in person or online.",
    "Join an online community or forum related to your hobbies or interests.",
    "Invite a colleague or friend for a virtual or in-person coffee chat.",
    "Participate in a group activity, like a workout class or a book club.",
    "Write a thoughtful comment on someone’s social media post.",
    "Schedule a catch-up call with an old friend or former colleague.",
    "Introduce yourself to a neighbor or coworker you haven’t met yet.",
    "Send a thank-you note or message to someone who helped you recently.",
    "Organize a small virtual gathering or game night with friends.",
)

val familyTasks = arrayListOf(


    "Plan and cook a meal together with your family.",
    "Organize a family game night with everyone’s favorite games.",
    "Call or video chat with a family member you don’t see often.",
    "Spend 15 minutes tidying up a shared space in the house.",
    "Go for a walk or bike ride with your family in the neighborhood.",
    "Create a family photo album or scrapbook with recent memories.",
    "Write a letter or note to a family member expressing your appreciation.",
    "Plan a fun outing or day trip with the whole family.",
    "Watch a movie together that everyone enjoys.",
    "Help a family member with a task or chore they’ve been putting off.",

    )

val musicTasks = arrayListOf(
    "Watch an episode of a TV show you’ve been meaning to start.",
    "Listen to a new album or playlist from a genre you don’t usually explore.",
    "Play a video game for 30 minutes or try a new mobile game.",
    "Read a few chapters of a book purely for enjoyment.",
    "Try your hand at a new hobby, like drawing, knitting, or photography.",
    "Watch a movie from a genre you rarely watch or from another country.",
    "Attend a virtual or local event, such as a concert, play, or comedy show.",
    "Explore a new podcast series in a topic that interests you.",
    "Spend some time outdoors, whether it’s a picnic, hiking, or just relaxing in a park.",
    "Create a playlist of your favorite songs and have a dance session at home.",
)