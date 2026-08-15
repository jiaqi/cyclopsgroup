# Open source

Before 2015, I was actively involved in several open-source projects, mostly in Java. Back then, if I saw somethin' missin' in the community, I'd just go ahead and build it as an open-source project.

## Java projects

The complete list of my open-source projects is on my [GitHub repositories](https://github.com/jiaqi?tab=repositories) page. A few of them turned out to be useful.

| Project | What is it | Why it's worth mentioning |
| -- | -- | -- |
| [jmxterm](https://github.com/jiaqi/jmxterm) | Command line alternative to JConsole | Well accepted by open source community |
| [datamung](https://github.com/jiaqi/datamung) | A web-based RDS MySQL data backup service | Won Netflix Cloud Prize award |
| [jcli](https://github.com/jiaqi/jcli) | Declarative command line parsing library in Java | Used by `jmxterm` and some other tools |

### Datamung

[https://github.com/jiaqi/datamung](https://github.com/jiaqi/datamung)

Among these projects, `datamung` was a Java web application that backed up RDS MySQL databases to S3 for cross-region and cross-account protection. At its core was a distributed workflow built on Amazon Simple Workflow, which was relatively new at the time. `datamung` went on to win the Netflix Cloud Prize in 2013. The ten winners were invited to the AWS re:Invent conference in Las Vegas to accept the award — it was even announced during Werner Vogels's keynote on November 14, 2013.

![AWS Re:Invent](https://miro.medium.com/v2/resize:fit:640/format:webp/1*TyxQYG6iEhvystxvozDR5g.png){: width="640"}

Ironically, not long after, AWS released their own cross-region RDS backup solution, which effectively made `datamung` obsolete. That's how it goes sometimes — big vendors step in and offer overlapping services. Even so, getting invited to re:Invent and being recognized by the community was an unforgettable experience.

### Jmxterm

[https://github.com/jiaqi/jmxterm](https://github.com/jiaqi/jmxterm)

The most popular project in my list has to be Jmxterm. It is a command line alternative to `jconsole` for Java. It aims to provide as much of `jconsole`'s functionality as possible in a non-graphical environment, including some features not officially exposed by the public JDK APIs. At the same time, it offers a rich interactive terminal experience that goes beyond what a typical Java library provides. It's one of the projects where I need it for work, couldn't find it, so I built it.

Jmxterm was [started around 2008](https://blog.cyclopsgroup.org/2008/07/replace-jconsole-with-command-line.html). Today it is in a maintenance mode because `jconsole` has not evolved in a long time. Some still consider it the best choice for headless access to Java MBeans. It's cited in several resources, including [53 Java libraries](https://emmanuelbernard.com/blog/2021/03/16/53-java-libraries/), operation guides in [Atlassian](https://support.atlassian.com/confluence/kb/how-to-get-jmx-data-from-confluence-using-jmxterm/) and [Cisco](https://www.cisco.com/c/en/us/td/docs/wireless/quantum-policy-suite/R23-2-0/vDRA-OperationsGuide/cps23-2-0vdraoperationsguide.pdf), and a few books.

## To contribute

If y'all share a passion for these projects and want to help out, please follow the standard GitHub development flow: open an issue, submit a pull request, and let's talk through changes there. I may not be as active as I once was, but I try to catch up on outstanding PRs now and then.

Each project has its development conventions, including lint rules, coding standards,  and test expectations. Most projects have CI/CD pipelines in place to help enforce these guidelines, although are not always perfect. When making changes, please make a best effort to follow the project's existing conventions and guidelines.

Thank you for your interest, contributions, and support.

## Post 2015

These days I'm not actively developing these open-source projects anymore, though several continue to be used and supported by volunteers in the broader community. Over time, my priorities've shifted.

While I'm still passionate about solving problems with software, the challenges I now focus on often require close, structured collaboration among people with diverse skills beyond programming. The solutions tend to be tightly coupled with surrounding enterprise systems. As a result, it has become challenging to create projects that is both useful and easy to maintain as an open-source. As a result, my work has shifted toward closed-source and commercial projects.

Personal life has played a big part too. As I've gotten older, family, kids, and other interests have naturally taken priority.
