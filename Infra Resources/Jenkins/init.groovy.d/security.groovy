//#!/usr/bin/env groovy
//
//import jenkins.model.*
//import hudson.security.*
//
//def instance = Jenkins.getInstance()
//
//def hudsonRealm = new HudsonPrivateSecurityRealm(false)
//hudsonRealm.createAccount("JENKINS_USER","JENKINS_PASS")
//instance.setSecurityRealm(hudsonRealm)
//
//def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
//instance.setAuthorizationStrategy(strategy)
//instance.save()
