Spring Mini Profiler
========================

This project is heavily based on the [mvc-mini-profiler](http://code.google.com/p/mvc-mini-profiler/) project for ASP.NET by the StackOverflow team.

It can be plugged into a Spring project, but right now the focus is on Spring MVC applications in order to provide profiler output right in the browser.

![This is what you'll get](http://www.niallthomson.com/blog/wp-content/uploads/2011/07/profiler-early.png)

Quick Start
-----------

This is the quickest possible way to get started, there is more configuration covered elsewhere.

First add the JAR using whatever dependency management system is appropriate.

You then need to add a servlet filter which starts the profiling and injects some assets and JSON into the finished output. The quickest way to do this is to add the following to web.xml:

    <filter>
        <filter-name>profilerFilter</filter-name>
            <filter-class>
                org.devcodes.miniprofiler.filter.ProfilerResponseFilter
        </filter-class>
    </filter>

    <filter-mapping>
        <filter-name>profilerFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

You then need to add a JSTL tag to allow stuff to be injected into your HTML output. The tag as to be put in the `<head>` section of your HTML, and how you do this will depend on what view system you're using. An example would be:

    <%@taglib uri="http://www.niallthomson.com/tags-profiler" prefix="profiler" %>
    <html>
        <head>
            <profiler:profiler-head />
        </head>
        <body>
        </body>
    </html>

DB Queries
----------

This requires an extra dependency and some extra configuration (coming soon...)

Using Local Assets
------------------

The browser-based profiler client requires a number of JS/CSS assets in order to function.  Its possible to configure the profiler to use assets hosted in a specific location. This is useful for speeding up download times of these assets or customising the profiler is some way.

First download the assets.zip file that corresponds to the version you are using. The contents of this archive should then be put in a single directory that is web-accessible (HTTP server, application server etc.)

Then it is just a matter of instructing the profiler where these assets are located so that it can adjust the HTML it generated accordingly. This is done by modifying 
the JSTL tag that you have already used, but specify the `localAssets` attribute. For example:

    <profiler:profiler-head localAssets="http://mysite.com/profiler-assets" />
    
The URL can be relative:

    <profiler:profiler-head localAssets="/profiler-assets" />

The TODO List
-------------

* Finish whats there
* Support AJAX queries
* Better formatting of SQL on client
* Add textual logging
* Historical profiler results
