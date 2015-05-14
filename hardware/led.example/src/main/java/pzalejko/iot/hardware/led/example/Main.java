/*******************************************************************************
 * Copyright (c) 2015 Pawel Zalejko(p.zalejko@gmail.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License Version 2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/
 *******************************************************************************/

package pzalejko.iot.hardware.led.example;

import java.io.IOException;

import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.gpio.GPIOPin;
import jdk.dio.gpio.GPIOPinConfig;

/**
 * 
 * This class shows a simple usage of the GPIO pins for controlling a LED.
 *
 */
public class Main {

	public static void blinkLEDByGPIONumber(int pinNumber) {
		System.out.println("Blinking LED on GPIO pin number " + pinNumber);
		GPIOPin pin = null;
		try {
			GPIOPinConfig pinConfig = new GPIOPinConfig(DeviceConfig.DEFAULT, pinNumber, GPIOPinConfig.DIR_OUTPUT_ONLY,
					GPIOPinConfig.MODE_OUTPUT_PUSH_PULL, GPIOPinConfig.TRIGGER_NONE, false);
			pin = (GPIOPin) DeviceManager.open(GPIOPin.class, pinConfig);
			boolean pinOn = false;
			for (int i = 0; i < 20; i++) {
				Thread.sleep(2500);
				pinOn = !pinOn;
				pin.setValue(pinOn);
			}
		} catch (IOException ioe) {
			throw new LEDExampleException(
					"IOException while opening device. Make sure you have the appropriate operating system permission to access GPIO devices.",
					ioe);
		} catch (InterruptedException ie) {
			// ignore
		} finally {
			try {
				System.out.println("Closing GPIO pin number " + pinNumber);
				if (pin != null) {
					pin.close();
				}
			} catch (Exception e) {
				throw new LEDExampleException("Received exception while trying to close device.", e);
			}
		}
	}

	public static void blinkLEDByDeviceId(int deviceId) {
		System.out.println("Blinking LED on device ID " + deviceId);
		GPIOPin pin = null;
		try {
			pin = (GPIOPin) DeviceManager.open(deviceId);
			boolean pinOn = false;
			for (int i = 0; i < 20; i++) {
				Thread.sleep(2500);
				pinOn = !pinOn;
				pin.setValue(pinOn);
			}
		} catch (IOException ioe) {
			throw new LEDExampleException(
					"IOException while opening device. Make sure you have the appropriate operating system permission to access GPIO devices.",
					ioe);
		} catch (InterruptedException ie) {
			// ignore
		} finally {
			try {
				System.out.println("Closing device ID " + deviceId);
				if (pin != null) {
					pin.close();
				}
			} catch (Exception e) {
				throw new LEDExampleException("Received exception while trying to close device.", e);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Setting properties...");

		// First, blink the LED by specifying the GPIO pin number
		blinkLEDByGPIONumber(18);
		// Next, blink the LED by looking up the device associated with
		// the device id
		blinkLEDByDeviceId(18);
	}
}

class LEDExampleException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;

	public LEDExampleException(String msg, Throwable t) {
		super(msg, t);
	}
}
