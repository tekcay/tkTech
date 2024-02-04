# Changelog

***

## v0.2 (GTCEu-tk-fork as dependency)

### Features
- added processes to produce sodium persulfate ([#36](https://github.com/tekcay/tkcy-simple-addon/pull/36))
- added the soldering iron from GT5u ([#36](https://github.com/tekcay/tkcy-simple-addon/pull/36))
- added `curvedPlate` ore prefix, its processing handler and a new machine ([#36](https://github.com/tekcay/tkcy-simple-addon/pull/36))
- added Steam Dust Mixer and Steam Melter ([#32](https://github.com/tekcay/tkcy-simple-addon/pull/32))
- added Steam Dust Mixer and Steam Melter ([#32](https://github.com/tekcay/tkcy-simple-addon/pull/32))
- added Steam Dust Mixer and Steam Melter ([#32](https://github.com/tekcay/tkcy-simple-addon/pull/32))
- added Steam Dust Mixer and Steam Melter ([#32](https://github.com/tekcay/tkcy-simple-addon/pull/32))
- added GT6 plates textures ([#31](https://github.com/tekcay/tkcy-simple-addon/pull/31))
- added harder cracking ([#25](https://github.com/tekcay/tkcy-simple-addon/pull/25))
- added harder hydrogenation processes and new multi for sulfuric fuels and ammonia synthesis ([#24](https://github.com/tekcay/tkcy-simple-addon/pull/24))
- added recipes for Cinnabar roasting ([#23](https://github.com/tekcay/tkcy-simple-addon/pull/23))
- added missing steps in Platinum and Rhodium chains ([#22](https://github.com/tekcay/tkcy-simple-addon/pull/22))
- added methane cracking recipes in the cracking unit and removed the GTCEu one.
Subsequent products must be distilled to recover the desired hydrogen
([#21](https://github.com/tekcay/tkcy-simple-addon/pull/21))


### Internal
- renamed mod and .jar archive ([#34](https://github.com/tekcay/tkcy-simple-addon/pull/34))
- removed all gtceu recipes removal methods ([#34](https://github.com/tekcay/tkcy-simple-addon/pull/34))
- added a new recipe builder to detect coil type ([#25](https://github.com/tekcay/tkcy-simple-addon/pull/25))
- added JEI module to hide ingredients ([#21](https://github.com/tekcay/tkcy-simple-addon/pull/21))

### Fixes
- fixed some cracking recipes not generated ([#35](https://github.com/tekcay/tkcy-simple-addon/pull/35))
- fixed shiny materials anode and cathode items not having +/- texture overlay ([#34](https://github.com/tekcay/tkcy-simple-addon/pull/34))
- fixed ore processing handlers not working properly by reorganized common proxy calls ([#34](https://github.com/tekcay/tkcy-simple-addon/pull/34))
- fixed foils recipes not being generated
- fixed electrodes recipes not being generated, missing texture overlay ([#30](https://github.com/tekcay/tkcy-simple-addon/pull/30))
- fixed brick buses and hatches being used in non-primitive multis ([#29](https://github.com/tekcay/tkcy-simple-addon/pull/29))


### Changes

- longer cracking recipe duration ([#36](https://github.com/tekcay/tkcy-simple-addon/pull/36))
- made components recipes even harder with the introduction of the `curvedPlate` ([#36](https://github.com/tekcay/tkcy-simple-addon/pull/36))

***

## v0.1 (GTCEu as 2.8.6 dependency)

### Features

- Removed circuit recipes that use tin as fluid input ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Added the commponent assembler and its recipemap ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Removed GTCEu recipes for components lv to iv and added new ones using soldering alloy in tge component assembler ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Added texture for lv fluid regulator ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Added ulv components ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Changed lv components textures for the GT6 ones ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Harder polarizer recipes ([#18](https://github.com/tekcay/tkcy-simple-addon/pull/18))
- Manganese chain ([#17](https://github.com/tekcay/tkcy-simple-addon/pull/17))
- Added harder coils and foils ([#16](https://github.com/tekcay/tkcy-simple-addon/pull/16))
- More alloys for harder casings, primitive alloying crucible to make alloys by mixing molten metals ([#15](https://github.com/tekcay/tkcy-simple-addon/pull/15))
- Gas release multiblock and recipes ([#14](https://github.com/tekcay/tkcy-simple-addon/pull/14))
- Fluorine chain ([#13](https://github.com/tekcay/tkcy-simple-addon/pull/13))
- Aluminium chain ([#12](https://github.com/tekcay/tkcy-simple-addon/pull/12))
- Added Galvanized Steel addition, production and usage, Primitive Roaster
and Fluid Blast Furnace, Advanced Electrolyzer, casting ([#11](https://github.com/tekcay/tkcy-simple-addon/pull/11))
- Zinc and germanium chains ([#8](https://github.com/tekcay/tkcy-simple-addon/pull/8))
- Roasting ([#7](https://github.com/tekcay/tkcy-simple-addon/pull/7))
- Tungsten chain ([#6](https://github.com/tekcay/tkcy-simple-addon/pull/6))
- Iron chain ([#4](https://github.com/tekcay/tkcy-simple-addon/pull/4))
- Gold chain ([#2](https://github.com/tekcay/tkcy-simple-addon/pull/2))
- Chromite chain ([#1](https://github.com/tekcay/tkcy-simple-addon/pull/1))

### Internal
- Updated GTCEu to 2.8.6 ([#20](https://github.com/tekcay/tkcy-simple-addon/pull/20))
- Updated lombok to 1.18.30 ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Added a component module and components tier orePrefixes ([#19](https://github.com/tekcay/tkcy-simple-addon/pull/19))
- Moved recipes registering to lowest loading part ([#16](https://github.com/tekcay/tkcy-simple-addon/pull/16))
- No energy and parallel logic, dynamic side length of a multiblock detection methods ([#14](https://github.com/tekcay/tkcy-simple-addon/pull/14))
- Updated to GTCEu 2.8.5, added Lombok dependency ([#10](https://github.com/tekcay/tkcy-simple-addon/pull/10))
- Added config options ([#5](https://github.com/tekcay/tkcy-simple-addon/pull/5))
- Updated mod version and infos ([#3](https://github.com/tekcay/tkcy-simple-addon/pull/3))


### Fixes

- Roasting iron, copper fixes ([#9](https://github.com/tekcay/tkcy-simple-addon/pull/9))



