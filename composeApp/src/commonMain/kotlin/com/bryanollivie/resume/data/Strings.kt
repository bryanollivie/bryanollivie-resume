package com.bryanollivie.resume.data

enum class AppLanguage { EN, PT }

data class AppStrings(
    // Tabs
    val tabSummary: String,
    val tabWorkHistory: String,
    val tabCertifications: String,
    // Section titles
    val summary: String,
    val languages: String,
    val education: String,
    val workHistory: String,
    val trainingCertifications: String,
    val technologies: String,
    val inProgress: String,
    // Drawer
    val settings: String,
    val notifications: String,
    val privacy: String,
    val shareResume: String,
    val about: String,
    // Personal info
    val title: String,
    val summaryText: String,
    // Work experiences
    val workExperiences: List<WorkExperienceStrings>,
    // Education
    val educationList: List<EducationStrings>,
    // Certifications
    val certificationList: List<CertificationStrings>,
    // Languages list
    val languageList: List<LanguageStrings>
)

data class WorkExperienceStrings(
    val company: String,
    val period: String,
    val role: String,
    val location: String,
    val highlights: List<String>,
    val technologies: String
)

data class EducationStrings(
    val year: String,
    val degree: String,
    val institution: String
)

data class CertificationStrings(
    val year: String,
    val name: String,
    val institution: String
)

data class LanguageStrings(
    val name: String,
    val level: String
)

object Strings {

    fun get(lang: AppLanguage): AppStrings = when (lang) {
        AppLanguage.EN -> en
        AppLanguage.PT -> pt
    }

    private val en = AppStrings(
        tabSummary = "Summary",
        tabWorkHistory = "Work History",
        tabCertifications = "Certifications",
        summary = "Summary",
        languages = "Languages",
        education = "Education",
        workHistory = "Work History",
        trainingCertifications = "Training & Certifications",
        technologies = "Technologies",
        inProgress = "In Progress",
        settings = "Settings",
        notifications = "Notifications",
        privacy = "Privacy",
        shareResume = "Share Resume",
        about = "About",
        title = "SOFTWARE ENGINEER",
        summaryText = "Software Engineer with 10+ years of experience in mobile development (Android, iOS, multiplatform) and backend systems, delivering apps used by over 10 million users in fintech, banking, and technology industries.\n\n" +
                "Has expertise in Kotlin, Java, Swift, Flutter, React Native, and Kotlin Multiplatform (KMP), with strong command of Clean Architecture, MVVM, CI/CD, RESTful APIs, and cloud integrations (AWS, Azure).\n\n" +
                "Experience with AI and LLMs for code analysis, automated testing, and performance optimization, achieving 25% fewer production bugs and 40% faster delivery cycles. Hands-on expertise in DevOps practices with Docker, Kubernetes, GitHub Actions, and Azure DevOps, ensuring scalable architectures and efficient automation pipelines.\n\n" +
                "Recognized for technical leadership, clear communication, problem-solving, agile collaboration, and adaptability in high-pressure environments.",
        workExperiences = listOf(
            WorkExperienceStrings(
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
            WorkExperienceStrings(
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
            WorkExperienceStrings(
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
        ),
        educationList = listOf(
            EducationStrings("2026", "Postgraduate degree in Applied Java Engineering", "Anhanguera - Brazil"),
            EducationStrings("2021", "MBA in Solution Architecture", "Fiap - Brazil"),
            EducationStrings("2014", "Android Engineer", "Institute of Interactive Arts (IAI) - Brazil"),
            EducationStrings("2013", "Linux LPI-101", "Jambu Tech. Engineering - Brazil"),
            EducationStrings("2013", "Bachelor's in Computer Science", "University Center of Pará - Brazil")
        ),
        certificationList = listOf(
            CertificationStrings("2025", "Kotlin Multiplatform (In Progress)", "Dio"),
            CertificationStrings("2025", "Flutter Specialist (In Progress)", "Dio"),
            CertificationStrings("2025", "LLM Training Processes (In Progress)", "Dio"),
            CertificationStrings("2024", "Security and Cloud Computing Essentials", "Itaú Unibanco"),
            CertificationStrings("2024", "Segurança da Informação", "Itaú Unibanco"),
            CertificationStrings("2023", "AWS Certified Cloud Practitioner (CLF-01)", "Amazon"),
            CertificationStrings("2020", "Scrum Master Professional Certificate (SMPC)", "CertiProf"),
            CertificationStrings("2016", "iOS Developer", "Udemy"),
            CertificationStrings("2014", "Android Developer", "Institute of Interactive Arts (IAI)")
        ),
        languageList = listOf(
            LanguageStrings("Portuguese", "Native or Bilingual"),
            LanguageStrings("English", "Advanced"),
            LanguageStrings("Spanish", "Advanced")
        )
    )

    private val pt = AppStrings(
        tabSummary = "Resumo",
        tabWorkHistory = "Experiência",
        tabCertifications = "Certificações",
        summary = "Resumo",
        languages = "Idiomas",
        education = "Formação",
        workHistory = "Experiência Profissional",
        trainingCertifications = "Treinamentos e Certificações",
        technologies = "Tecnologias",
        inProgress = "Em Andamento",
        settings = "Configurações",
        notifications = "Notificações",
        privacy = "Privacidade",
        shareResume = "Compartilhar Currículo",
        about = "Sobre",
        title = "ENGENHEIRO DE SOFTWARE",
        summaryText = "Engenheiro de Software com mais de 10 anos de experiência em desenvolvimento mobile (Android, iOS, multiplataforma) e sistemas backend, entregando aplicativos utilizados por mais de 10 milhões de usuários nos setores de fintech, bancos e tecnologia.\n\n" +
                "Possui expertise em Kotlin, Java, Swift, Flutter, React Native e Kotlin Multiplatform (KMP), com sólido domínio de Clean Architecture, MVVM, CI/CD, APIs RESTful e integrações em nuvem (AWS, Azure).\n\n" +
                "Experiência com IA e LLMs para análise de código, testes automatizados e otimização de performance, alcançando 25% menos bugs em produção e 40% mais velocidade nos ciclos de entrega. Experiência prática em DevOps com Docker, Kubernetes, GitHub Actions e Azure DevOps, garantindo arquiteturas escaláveis e pipelines de automação eficientes.\n\n" +
                "Reconhecido por liderança técnica, comunicação clara, resolução de problemas, colaboração ágil e adaptabilidade em ambientes de alta pressão.",
        workExperiences = listOf(
            WorkExperienceStrings(
                company = "Nova Pay",
                period = "06-2025 - Atual",
                role = "Engenheiro de Software Sênior",
                location = "São Paulo, Brasil",
                highlights = listOf(
                    "Entregou funcionalidades críticas em aplicações Flutter de alta escala servindo mais de 1M de usuários ativos diários, garantindo estabilidade da plataforma, conformidade de segurança e experiência do usuário.",
                    "Arquitetou e implementou soluções mobile escaláveis usando princípios de clean architecture, integrando APIs RESTful e sistemas de mensageria orientados a eventos, aumentando o suporte a usuários simultâneos em 30%.",
                    "Projetou e otimizou pipelines CI/CD, reduzindo o tempo de deploy em 40%, melhorando a confiabilidade dos releases e minimizando intervenção manual.",
                    "Utilizou testes assistidos por IA e análise estática de código para melhorar a qualidade, reduzindo defeitos em produção em 25% e acelerando ciclos de code review em 15%.",
                    "Monitorou proativamente performance de aplicação e API usando Datadog e Splunk, mantendo tempos de resposta médios <200ms e sustentando 99.9% de disponibilidade (SLA)."
                ),
                technologies = "Dart, Flutter; Go; GraphQL; GitHub Actions; DevOps; Docker; Kubernetes; AWS; Datadog; Angular; Webview; Amplitude; Linear"
            ),
            WorkExperienceStrings(
                company = "Itaú Unibanco",
                period = "03-2024 - 06-2025",
                role = "Engenheiro de Software Sênior",
                location = "São Paulo, Brasil",
                highlights = listOf(
                    "Entregou funcionalidades mobile críticas para apps Android com mais de 10M de usuários diários, garantindo estabilidade e segurança.",
                    "Projetou arquiteturas escaláveis, integrando APIs RESTful e sistemas de mensageria, suportando 30% mais usuários simultâneos.",
                    "Automatizou pipelines CI/CD, reduzindo o tempo de deploy em 40% e minimizando erros humanos.",
                    "Aplicou testes e análise de código orientados por IA, reduzindo bugs em produção em 25% e melhorando a velocidade de code review em 15%.",
                    "Monitorou performance com Datadog e Splunk, mantendo tempos de resposta de API <200ms e alcançando 99.9% de uptime."
                ),
                technologies = "Kotlin; Java; Spring Boot; Android Jetpack Compose; Swift; iOS; RESTful APIs; GitHub Actions; Azure DevOps; Docker; Kubernetes; AWS; Datadog; Splunk; Angular; Webview; Cordova"
            ),
            WorkExperienceStrings(
                company = "Alelo S.A",
                period = "01-2023 - 03-2024",
                role = "Engenheiro de Software Sênior",
                location = "São Paulo, Brasil",
                highlights = listOf(
                    "Construiu um novo módulo de rede de aceitação, permitindo integração com parceiros e suportando milhares de transações diárias.",
                    "Liderou a arquitetura, desenvolvimento e lançamento do app Alelo Pod, que alcançou mais de 100K downloads nos primeiros meses.",
                    "Entregou funcionalidades cross-platform baseadas em Flutter, melhorando os índices de satisfação do usuário em 20%.",
                    "Automatizou pipelines usando Azure DevOps e GitHub Actions, reduzindo o tempo de integração em 35% e aumentando a frequência de releases."
                ),
                technologies = "Kotlin; Flutter; Android Jetpack; Compose; Swift; iOS; RESTful APIs; Cordova; GitHub Actions; Azure DevOps; Docker; Kubernetes; AWS; Datadog"
            )
        ),
        educationList = listOf(
            EducationStrings("2026", "Pós-graduação em Engenharia Java Aplicada", "Anhanguera - Brasil"),
            EducationStrings("2021", "MBA em Arquitetura de Soluções", "Fiap - Brasil"),
            EducationStrings("2014", "Engenheiro Android", "Instituto de Artes Interativas (IAI) - Brasil"),
            EducationStrings("2013", "Linux LPI-101", "Jambu Tech. Engineering - Brasil"),
            EducationStrings("2013", "Bacharelado em Ciência da Computação", "Centro Universitário do Pará - Brasil")
        ),
        certificationList = listOf(
            CertificationStrings("2025", "Kotlin Multiplatform (Em Andamento)", "Dio"),
            CertificationStrings("2025", "Flutter Specialist (Em Andamento)", "Dio"),
            CertificationStrings("2025", "Processos de Treinamento LLM (Em Andamento)", "Dio"),
            CertificationStrings("2024", "Fundamentos de Segurança e Cloud Computing", "Itaú Unibanco"),
            CertificationStrings("2024", "Segurança da Informação", "Itaú Unibanco"),
            CertificationStrings("2023", "AWS Certified Cloud Practitioner (CLF-01)", "Amazon"),
            CertificationStrings("2020", "Scrum Master Professional Certificate (SMPC)", "CertiProf"),
            CertificationStrings("2016", "iOS Developer", "Udemy"),
            CertificationStrings("2014", "Desenvolvedor Android", "Instituto de Artes Interativas (IAI)")
        ),
        languageList = listOf(
            LanguageStrings("Português", "Nativo ou Bilíngue"),
            LanguageStrings("Inglês", "Avançado"),
            LanguageStrings("Espanhol", "Avançado")
        )
    )
}
