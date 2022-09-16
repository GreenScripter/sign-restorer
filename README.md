# Sign Restorer Fabric Mod
Easily replace signs in game using saved data from other programs.  
Sign data is read from this repo in the format:  
`[{"coordinates":"128 67 -73","text":["EnderKill98","was here","","14-09-2022"]}]`  
Type `&reloadsigns` in game chat to reload the sign data.  
Type `&reloadconfig` in game chat to reload the configuration.  
### Config
Change the url to get the sign data from in the config.
Add a file entry to the config to load from a file instead.
```
{
  "url": "https://raw.githubusercontent.com/GreenScripter/sign-restorer/master/signData.json",
  "file": "signdata.json"
}
```
