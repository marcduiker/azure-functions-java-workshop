# Workshop: Building Azure Functions in Java

In case you did not attend my brief introduction on Building Azure Functions in Java, have a [look at the slides](https://speakerdeck.com/marcduiker/building-azure-functions-in-java).

## Goal

The goal is to get a feel what the development experience is when writing Java based Azure Functions using VS Code.

## Prerequisites

Please download and install [VS Code](https://code.visualstudio.com/Download) and follow [these instructions](https://code.visualstudio.com/docs/java/java-azurefunctions) to install the required tooling.

In order to develop completely locally and not rely on the cloud you can install one of these storage emulators:
- [Azure Storage Emulator](https://go.microsoft.com/fwlink/?linkid=717179&clcid=0x409) (For Windows)
- [Azurite extension for VSCode](https://marketplace.visualstudio.com/items?itemName=Azurite.azurite) (Cross platform)

## Documentation

[Azure Functions Java developer guide](https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java)

## Use case

Most conferences like to get feedback from the attendees about the sessions or workshops they followed. In the next 3 labs you will implement the back-end part of this functionality by writing Azure Functions in Java to capture and store the feedback.

## High level solution

The end situation will be that a POST is done to your HttpTrigger function with the attendee feedback in the request body. A message with the feedback is put on a queue. Then a QueueTriggered function will pick up this message and store this in Table Storage.

```
HTTP POST  --->  Queue  --->  Table
```

## Labs

- [Lab 1 - Default Function](/labs/lab1.md)
- [Lab 2 - HttpTrigger with queue output](/labs/lab2.md)
- [Lab 3 - QueueTrigger with table output](/labs/lab3.md)