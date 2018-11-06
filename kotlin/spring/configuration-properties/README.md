## Overview

Demonstrates how to load YAML data into spring boot application

## Main Points

* define *@PropertySource* on a *@Configuration* class to load properties into spring context
* define *@ConfigurationProperties* on a properties object representation to let spring populate its properties (using target prefix as a filter)

## Notes

*@PropertySource* doesn't support YAML out of the box, that's why we explicitly specify YAML *PropertySourceFactory* in the *@PropertySource*