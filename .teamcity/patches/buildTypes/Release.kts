package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Release'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Release")) {
    params {
        add {
            password("env.ORG_GRADLE_PROJECT_youtrackToken", "credentialsJSON:3cd3e867-282c-451f-b958-bc95d56a8450", display = ParameterDisplay.HIDDEN)
        }
    }

    expectSteps {
        gradle {
            tasks = "clean publishPlugin slackNotification"
            buildFile = ""
            enableStacktrace = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
    }
    steps {
        update<GradleBuildStep>(0) {
            clearConditions()
            tasks = "clean publishPlugin"
        }
        insert(1) {
            gradle {
                name = "Run Integrations"
                tasks = "releaseActions"
                param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            }
        }
        insert(2) {
            gradle {
                name = "Slack Notification"
                enabled = false
                tasks = "slackNotification"
                param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            }
        }
    }
}
