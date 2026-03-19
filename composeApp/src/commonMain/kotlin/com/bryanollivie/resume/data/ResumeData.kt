package com.bryanollivie.resume.data

data class PersonalInfo(
    val name: String,
    val title: String,
    val address: String,
    val phone: String,
    val email: String,
    val linkedin: String,
    val summary: String,
    val languages: List<Language>
)

data class Language(
    val name: String,
    val level: String
)

data class Education(
    val year: String,
    val degree: String,
    val institution: String
)

data class WorkExperience(
    val company: String,
    val period: String,
    val role: String,
    val location: String,
    val highlights: List<String>,
    val technologies: String
)

data class Certification(
    val year: String,
    val name: String,
    val institution: String
)

object ResumeData {

    val personalInfo = PersonalInfo(
        name = "Bryan Ollivie Cunha de Souza",
        title = "SOFTWARE ENGINEER",
        address = "São Paulo - SP, Brazil",
        phone = "+5511971721750",
        email = "bryanollivie@gmail.com",
        linkedin = "https://www.linkedin.com/in/bryanollivie/",
        summary = "Software Engineer with 10+ years of experience in mobile development (Android, iOS, multiplatform) and backend systems, delivering apps used by over 10 million users in fintech, banking, and technology industries.\n\n" +
                "Has expertise in Kotlin, Java, Swift, Flutter, React Native, and Kotlin Multiplatform (KMP), with strong command of Clean Architecture, MVVM, CI/CD, RESTful APIs, and cloud integrations (AWS, Azure).\n\n" +
                "Experience with AI and LLMs for code analysis, automated testing, and performance optimization, achieving 25% fewer production bugs and 40% faster delivery cycles. Hands-on expertise in DevOps practices with Docker, Kubernetes, GitHub Actions, and Azure DevOps, ensuring scalable architectures and efficient automation pipelines.\n\n" +
                "Recognized for technical leadership, clear communication, problem-solving, agile collaboration, and adaptability in high-pressure environments.",
        languages = listOf(
            Language("Portuguese", "Native or Bilingual"),
            Language("English", "Advanced"),
            Language("Spanish", "Advanced")
        )
    )

    val education = listOf(
        Education("2026", "Postgraduate degree in Applied Java Engineering", "Anhanguera - Brazil"),
        Education("2021", "MBA in Solution Architecture", "Fiap - Brazil"),
        Education("2014", "Android Engineer", "Institute of Interactive Arts (IAI) - Brazil"),
        Education("2013", "Linux LPI-101", "Jambu Tech. Engineering - Brazil"),
        Education("2013", "Bachelor's in Computer Science", "University Center of Pará - Brazil")
    )

    val workHistory = listOf(
        WorkExperience(
            company = "Nova Pay",
            period = "06-2025 - Present",
            role = "Senior Software Engineer",
            location = "São Paulo, Brazil",
            highlights = listOf(
                "Delivered critical features in high-scale Flutter applications serving 1M+ daily active users, ensuring platform stability, security compliance, and seamless user experience.",
                "Architected and implemented scalable mobile solutions using clean architecture principles, integrating RESTful APIs and event-driven messaging systems, increasing support for concurrent users by 30% without degradation in performance.",
                "Designed and optimized CI/CD pipelines, reducing deployment time by 40%, improving release reliability, and significantly minimizing manual intervention and human error.",
                "Leveraged AI-assisted testing and static code analysis to enhance code quality, reducing production defects by 25% and accelerating code review cycles by 15%.",
                "Proactively monitored application and API performance using Datadog and Splunk, maintaining <200ms average response times and sustaining 99.9% service availability (SLA compliance)."
            ),
            technologies = "Dart, Flutter; Go; GraphQL; GitHub Actions; DevOps; Docker; Kubernetes; AWS; Datadog; Angular; Webview; Amplitude; Linear"
        ),
        WorkExperience(
            company = "Itaú Unibanco",
            period = "03-2024 - 06-2025",
            role = "Senior Software Engineer",
            location = "São Paulo, Brazil",
            highlights = listOf(
                "Delivered critical mobile features for Android apps with 10M+ daily users, ensuring stability and security.",
                "Designed scalable architectures, integrating RESTful APIs and messaging systems, supporting 30% more concurrent users.",
                "Automated CI/CD pipelines, reducing deployment time by 40% and minimizing human errors.",
                "Applied AI-driven testing and code analysis, cutting production bugs by 25% and improving code review speed by 15%.",
                "Monitored performance with Datadog and Splunk, maintaining API response times <200ms and achieving 99.9% uptime."
            ),
            technologies = "Kotlin; Java; Spring Boot; Android Jetpack Compose; Swift; iOS; RESTful APIs; GitHub Actions; Azure DevOps; Docker; Kubernetes; AWS; Datadog; Splunk; Angular; Webview; Cordova"
        ),
        WorkExperience(
            company = "Alelo S.A",
            period = "01-2023 - 03-2024",
            role = "Senior Software Engineer",
            location = "São Paulo, Brazil",
            highlights = listOf(
                "Built a new acceptance network module, enabling integration with partners and supporting thousands of transactions daily.",
                "Led the architecture, development, and launch of the Alelo Pod app, which reached 100K+ downloads in the first months.",
                "Delivered Flutter-based cross-platform features, improving user satisfaction scores by 20%.",
                "Automated pipelines using Azure DevOps and GitHub Actions, reducing integration time by 35% and increasing release frequency."
            ),
            technologies = "Kotlin; Flutter; Android Jetpack; Compose; Swift; iOS; RESTful APIs; Cordova; GitHub Actions; Azure DevOps; Docker; Kubernetes; AWS; Datadog"
        )
    )

    val certifications = listOf(
        Certification("2025", "Kotlin Multiplatform (In Progress)", "Dio"),
        Certification("2025", "Flutter Specialist (In Progress)", "Dio"),
        Certification("2025", "LLM Training Processes (In Progress)", "Dio"),
        Certification("2024", "Security and Cloud Computing Essentials", "Itaú Unibanco"),
        Certification("2024", "Segurança da Informação", "Itaú Unibanco"),
        Certification("2023", "AWS Certified Cloud Practitioner (CLF-01)", "Amazon"),
        Certification("2020", "Scrum Master Professional Certificate (SMPC)", "CertiProf"),
        Certification("2016", "iOS Developer", "Udemy")
    )
}
