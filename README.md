# BuildItBigger - Project 4 Android Nanodegree
* App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.

## Main features
* App has two flavors (free and paid).
* App has an Android Library which show jokes in a separate activity.
* Jokes supplied from GCE (from another Java library).
* Free version has ads (banner ad, interstitial ad).
* App has connected tests to check that data fetches from GCE correctly.
* Gradle build has a task, which starts local GCE, tests the app and shutdowns the GCE.

## Used technologies
* Gradle, scripts.
* Android Connected Tests.
* Google Ads API.
* Addiditonal Java and Android Libraries.
* Google Cloud Endpoints (both local and online).

## Android Library
* Depending on the flavor of the app library either shows a not-so-funny joke, or downloads a comics from xkcd.
* Uses libraries: Picasso, GSON, OkHttp.