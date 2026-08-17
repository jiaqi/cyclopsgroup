# Open source

Before 2015, I was actively involved in several open-source projects, mostly in Java. Back then, if I saw somethin' missin' in the community, I'd just go ahead and build it as an open-source project.

## Java projects

The complete list of my open-source projects is on my [GitHub repositories](https://github.com/jiaqi?tab=repositories) page. Among all these projects, [jmxterm](https://github.com/jiaqi/jmxterm) and [datamung](https://github.com/jiaqi/datamung) are two Java projects worth calling out.

### Datamung

[https://github.com/jiaqi/datamung](https://github.com/jiaqi/datamung)

Project `datamung` is a Java web application that backs up RDS MySQL database into S3 to achieve cross-region and cross-account backup. The core of it is a distributed workflow built on Amazon Simple Workflow which was just introduced to the industry at the time of the project. It was one of the winners of [the Netflix Cloud Prize](https://netflixtechblog.com/netflix-open-source-software-cloud-prize-winners-5a15d87a9ed7) in 2013. The winners were invited to the second AWS Re:Invent conference in Las Vegas to accept the award, which [was announced](https://www.youtube.com/watch?v=Waq8Y6s1Cjs) during Wener Vogel's keynote on November 14, 2013.

![AWS Re:Invent](https://miro.medium.com/v2/resize:fit:640/format:webp/1*TyxQYG6iEhvystxvozDR5g.png){: width="640"}

Ironically, not long after, AWS released its own cross-region RDS backup solution, which rendered `datamung` obsolete. It was a good example that the tech giants would not hesitate to compete with open-source tools that served similar purposes. Despite the short life of the tool, it was a memorable experience to be invited to Re:Invent in person and recognized by the community.

### Jmxterm

[https://github.com/jiaqi/jmxterm](https://github.com/jiaqi/jmxterm)

The most popular project in my list has to be Jmxterm. It is a command line alternative to `jconsole` for Java. It aims to provide as much of `jconsole`'s functionality as possible in a non-graphical environment, including some features not officially exposed by the public JDK APIs. At the same time, it offers a rich interactive terminal experience that goes beyond what a typical Java library can do. It's one of the projects where I need it for work, couldn't find it, so I built it.

Jmxterm was [started around 2008](https://blog.cyclopsgroup.org/2008/07/replace-jconsole-with-command-line.html). Today it is in a maintenance mode because `jconsole` has not evolved in a long time. Some still consider it the best choice for headless access to Java MBeans. It's cited in several resources, including [53 Java libraries](https://emmanuelbernard.com/blog/2021/03/16/53-java-libraries/), operation guides in [Atlassian](https://support.atlassian.com/confluence/kb/how-to-get-jmx-data-from-confluence-using-jmxterm/) and [Cisco](https://www.cisco.com/c/en/us/td/docs/wireless/quantum-policy-suite/R23-2-0/vDRA-OperationsGuide/cps23-2-0vdraoperationsguide.pdf), and a few books.

## To contribute

For anyone who shares a passion for these projects and want to contribute, please follow the standard GitHub development flow, submit a pull request, and we can discuss in the PR. I may not be as active as before, but I try to catch up on outstanding PRs from time to time.

Each project has its development conventions, including lint rules, coding standards,  and test expectations. Most projects have CI/CD pipelines in place to help enforce these guidelines, although are not always perfect. When making changes, please make a best effort to follow the project's existing conventions and guidelines.

Thank you for your interest, contributions, and support.

## Post 2015

These days I'm not actively developing mentioned open-source projects anymore, though several continue to be used and supported by volunteers in the broader community. Over time, my priorities have shifted.

While I'm still passionate about solving problems with software, the challenges I now focus on often require close, structured collaboration between people with diverse skills beyond programming and technology. The solutions tend to be tightly coupled with surrounding enterprise systems. As a result, it has become challenging to create projects that is both useful and easy to maintain as open-source and my work has shifted toward closed-source and commercial projects.

Personal life has played a big part too. As I've gotten older, family, children, and other personal interests have naturally taken priority.
