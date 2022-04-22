# DazuBoss
Boss plugin for Dazunaki

##How to use
/dazuboss set_vanilla (key) (type) (nbt): register a new boss from vanilla nbt tags

/dazuboss set_magic (key) (name): register a new boss from magic plugin

/dazuboss spawn (key): summons a registered boss

/dazuboss list: shows the list of all registered bosses

/dazuboss name|location|alivecheck (key) (value): configure the boss' name|location|alivecheck settings

/dazuboss remove (key): remove the registered boss

/dazuboss config delay_base|delay_random (value): configure the delay in ticks. the delay is randomly selected between delay_base-delay_random and delay_base+delay_random

/dazuboss defeat: let the plugin know that the boss is defeated. note that it does not actually kill the boss

##Permissions
dazuboss.admin: grants an ability to execute the commands above
