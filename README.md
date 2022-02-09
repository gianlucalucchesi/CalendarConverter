# CalendarConverter

Compares an old and new .ics (calendar) file and creates a new .ics keeping only the new events.

## Created runnable jar containing JavaFX
https://www.youtube.com/watch?v=busqPViapBQ&t=336s

## "No main manifest error" while running jar (built with IntelliJ)
https://linuxpip.org/fix-no-main-manifest-attribute/

People have been reporting that IntelliJ IDEA keeps putting the JAR artifact into the wrong folder.

In order to fix "no main manifest attribute" in IntelliJ IDEA, choose JAR > From modules with dependencies

From modules with dependencies

In the Create JAR from Modules window, change the default Directory for META-INF/MANIFEST.MF path from <project folder>\src\main\java to <project folder>\src\main\resources.

Otherwise it would generate the manifest and including it in the jar, but not the one in <project folder>\src\main\java that Java expects.

After that, just continue to Build Artifacts as you usually do.
