IoT-temperature
=============

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ffdf4ac295574e49a2ebbb1d92edfc2b)](https://app.codacy.com/app/p-zalejko/IoT-temperature?utm_source=github.com&utm_medium=referral&utm_content=p-zalejko/IoT-temperature&utm_campaign=Badge_Grade_Settings)

A very simple project which collects a temperature value from the DS18B20 sensor and sends it out using a MQTT client.

Video: https://www.youtube.com/watch?v=zlS7tIBRaxU

Technologies
-------
* Java
* OpenJDK: Device I/O Project
* Eclipse Paho
* Android
* Spring Framework(Boot)
* SockJS
* Chart.js


Android application
-------
You can find it [here](https://github.com/p-zalejko/IoT-temperature/tree/master/client/mobile/IoTHome)

Web application
-------
You can find it [here](https://github.com/p-zalejko/IoT-temperature/tree/master/client/web)

Raspberry Pi(sensors)
-------
You can find it [here](https://github.com/p-zalejko/IoT-temperature/tree/master/hardware)

**Schematic diagram**:
![](https://github.com/p-zalejko/IoT-temperature/blob/master/hardware/home.core/pi_schematic.png?raw=true "")

Raspberry Pi needs to be configured in order to be able to read a temperature from the DS18B20 sensor. [See](http://raspberrywebserver.com/gpio/connecting-a-temperature-sensor-to-gpio.html) how to do it. See also how to [load drivers at startup](http://raspberrypi.znix.com/hipidocs/topic_load_drivers_startup.htm).

**How to launch the application on Raspberry Pi**:

1. Build the project

    `mvn clean install`

2. Upload the following folder to Raspberry Pi:

    `iot.home\hardware\home.core\target\product`
	
3. Launch the `product\launcher\launch_linux.sh` file. Please make sure the `launch_linux.sh` file has execute permission.