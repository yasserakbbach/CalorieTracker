apply {
    from("$rootDir/compose-base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.onboardingDomain))
}