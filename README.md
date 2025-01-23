# SafePets
**SafePets** is a Minecraft plugin designed for servers running version b1.7.3.

It prevents players from harming others' pets.

---
## Contributing Code & Reporting Issues
Consider helping SafePets become even more versatile and robust.

Visit the [CONTRIBUTING](https://github.com/AleksandarHaralanov/SafePets/blob/master/.github/CONTRIBUTING.md) guide for details on how to get started and where to focus your efforts.

For any issues with the plugin, or suggestions, please report them [here](https://github.com/AleksandarHaralanov/SafePets/issues).

---
## Download
Latest releases of **SafePets** can be found here on [GitHub](https://github.com/AleksandarHaralanov/SafePets/releases).<br>

The plugin is fully open-source and transparent.<br>
If you'd like additional peace of mind, you're welcome to scan the `.jar` file using [VirusTotal](https://www.virustotal.com/gui/home/upload).

---
## Requirements
Your b1.7.3 server must be running one of the following APIs: CB1060-CB1092, [Project Poseidon](https://github.com/retromcorg/Project-Poseidon) or [UberBukkit](https://github.com/Moresteck/Project-Poseidon-Uberbukkit).

---
## Usage
By default, only OPs have permission.

Use PermissionsEx or similar plugins to grant groups the permission, enabling the commands.

### Permissions:
- `safepets.bypass` - Allows player to bypass the SafePets protection.

---
## Configurations
Generates `alive-pets.yml` and `dead-pets.yml` located at `plugins/SafePets/pets`.

Both configurations update dynamically as players interact with entities and rejoin the server.

### Alive Pets
Registers the unique IDs of pets that are currently alive—either tamed after the plugin was added or interacted with by their owner if they were tamed before the plugin was added.

### Dead Pets
Registers the unique IDs of pets that were killed while their owner was offline.

> [!WARNING]
> Wolf pets tamed before this plugin was added will not be automatically included in the configurations.
>
> They are only added to the `alive-pets.yml` config when their owner interacts with them by right-clicking.
>
> Until they are added, no one, including their owner, can harm the pets.
