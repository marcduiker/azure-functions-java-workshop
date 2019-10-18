# Workshop: Creating Azure Functions in Java

## Goal

The goal is to understand what Azure Functions is, how you can use it as part of an event-driven architecture and to get a feel what the development experience is when Java based Azure Functions are writting using VS Code.

## Prerequisites

Please download and install [VS Code](https://code.visualstudio.com/Download) and follow [these instructions](https://code.visualstudio.com/docs/java/java-azurefunctions) to install the required tooling.

In order to develop completely locally and not rely on the cloud you can install one these storage emulators:
- [Azure Storage Emulator](https://go.microsoft.com/fwlink/?linkid=717179&clcid=0x409) (For Windows)
- [Azurite extension for VSCode](https://marketplace.visualstudio.com/items?itemName=Azurite.azurite) (Cross platform)

## Labs

- [Lab 1 - Default Function](lab1.md)
- [Lab 2 - HttpTrigger with queue output](lab2.md)
- [Lab 3 - QueueTrigger with table output](lab3.md)