# Open source

Before 2015 I was actively involved in several open source projects, mostly in Java. At the time, the mindset is whatever feels missing in the open community, I would go ahead and implement it as an open source project. A few of them ended up somewhat fruitful.

| Project | What is it | Why it's worth mentioning |
| -- | -- | -- |
| [jmxterm](https://github.com/jiaqi/jmxterm) | Command line alternative to JConsole | Well accepted by open source community |
| [datamung](https://github.com/jiaqi/datamung) | A web-based RDS MySQL data backup service | Won Netflix Cloud Prize award |
| [jcli](https://github.com/jiaqi/jcli) | Declarative command line parsing library in Java | Used by jmxterm and some other tools |

The complete list of all my open source projects can be found in my [GitHub repositories](https://github.com/jiaqi?tab=repositories) page.

Among these projects, `datamung`, a Java web application that baks up RDS MySQL database into S3 to achieve cross-region and cross-account backup. It was one of the winners of [the Netflix Cloud Prize](https://netflixtechblog.com/netflix-open-source-software-cloud-prize-winners-5a15d87a9ed7) in 2013. The ten winners were invited to the second AWS Re:Invent conference in Las Vegas to receive the award, which was announced during Wener Vogel's keynote on November 14, 2013. It was a memorable experience to be there in person and be recognized by the community.

![AWS Re:Invent](https://miro.medium.com/v2/resize:fit:640/format:webp/1*TyxQYG6iEhvystxvozDR5g.png){: width="640"}

Ironically, not long after, AWS released its own cross-region RDS backup solution, which rendered `datamung` obsolete. It was a good example that the tech giants would not hesitate to compete with open-source tools that served similar purposes.

## To contribute

For anyone who shares a passion for these projects and would like to contribute, please follow the standard Github development flow. Feel free to submit pull requests and discuss changes there. I may not be as active, but I still try to catch up on outstanding PRs from time to time.

Each project has its development conventions, including lint rules, coding standards,  and test expectations. Most projects have CI/CD pipelines in place to help enforce these guidelines, although are not always perfect. When making changes, please make a best effort to follow the project's existing conventions and guidelines.

Thank you for your interest, contributions, and support.

## Post 2015

As of now I am no longer actively making progress in these open-source projects, though several continue to be used and supported by volueers thanks to the broader open-source community. Over time, my priorities have naturally shifted.

While I remain passionate about solving problems with computer programming, the challenges I now focus on often rely on close, complex and well-structured collaboration across people with diverse skills and expertise, something open source with casual collaboration does not always support effectively. As a result, my software involvement has been more and more geared towards close source projects.

Personal life has also played an important role. As I have grown older, family, children and other personal interests have become increasingly important and have naturually taken priority.
