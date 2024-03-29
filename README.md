# distributed-system
This Distributed system repository is the quick Onramp for people that want to understand how distributed-system, be prepared to hands-on and real-life Labs.

# Monolithic application
All-In-One, all modules are tightly coupled , you can't modify one without possibly affecting another.
![Monolithic Logo](/docs/1-Monolithic.png)
- [monolithic-patterns](https://microservices.io/patterns/monolithic.html)

# Monolithic Enterprise application
I called this one monolithic Enterprise, maybe is not the right name, but I see very commons in monilothic application for ingrease the Write and Read capacity, company decide todo add Cache Layer like Redis for speed the Reading, Queue System like Kafka for spead the writing, and Search engine database like Elasticsearch.

Hint:  Distributed System using Microservices you can just scale a particar services to increase the Read/Write capacity with just a simple ' scale --replicas=3 ', also Cache, Queue and Search Engine are used in big system, not always the solution is add it a new complexity layer! try to solve the problem firts from the best system design point of view, then choose the technology.
![Monolithic enterprise Logo](/docs/2-Monolithic-enterprise.png)
- [monolithic-patterns](https://microservices.io/patterns/monolithic.html)

# Microservices & Distributed System
In this full labs series you can say "Wow finally we arrived to the distributed system", but at least in the currenty year 2021,  when this articule was write, we still have a lot monolythic application in the biggers well-know Company, also the process to move to microservices looks far from some Business area & Technical department, but try to understand well the Monolithics application, what is the current advantage, disadvantage, understand also what value we can add to the business if we change to microservices architecture and distributed system. for example: we be able to develop and delivery features faster, do a onboarding faster and the new technical members, this migration will decrease the learn curve progressive working a particular services and not the whole company product.
![Microservices Logo](/docs/3-microservices-and-distributed-system.png)
- [microservices-patterns](https://microservices.io/patterns/microservices.html)
- [Container Orchestration-Kubernetes ](https://kubernetes.io/)

# Clarification #1
distributed-system ≠ high-performance computing(HPC): In some text books talk about this two terms like the same, but in this case let talk about like complete differents topics.

- distributed-system: Containers oriented, CI-CD Pipeline, quickly scale, support intensive load. we will used software like Docker, Kubernetes, Jenkins, Git, ELK and programming languages like Java11+, Python, Nodejs14+, ReactJS17+.

- HPC: Run big workloads for certains use case like Scienties, Usually running this on SuperComputer with special hardware with a lot CPU, RAM, GPU. we will use C, C++, OpenMP, OpenHPC, OpenMPI and Linux, HPC is NOT a topic to work or explorer here in this repo.

# Clarification #2 
The goals of this repo is have a Professional development setup ready, is not 101 Java, not 101 Spring Boot, not 101 Microservices, not 101 Kubernetes, not 101 ReactJS, not 101 C++, if you never take a training in this technology, I highly recomend go get the fudamentals knowlaged, do small project like todo-list and then return to this repo.

### Author
- Tom
- Jun 21, 2021 · 5 min read - 5 hours labs for play around with the 3 projects.
- Follow me on Twitter: https://twitter.com/tomj0101 Github: https://github.com/tomj0101


### important
```
The Dev Machine setup README with all the installer is not something the I need to run everyday, that means if you read this article in 3 years or in 300 year, probably the version and dependency will not be the same, well probably the programming languages will not be the same too :)
Link: https://github.com/tomj0101/distributed-system/tree/main/0-dev-machine-setup
```


