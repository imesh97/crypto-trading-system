# Cryptocurrency Trading System - CS2212

## Authors
Gurshawn Singh Lehal (glehal)\
Imesh Nimsitha (inimsith)\
Uzair Muhammed Salim (usalim2)\
Gunveer Vilkhu (gvilkhu)

## Overview
This project aims to implement a system that performs and manages trading strategies in cryptocurrency, along with the aid of a graphical user interface and other features. The CoinGecko API will be utilized for the retrieval of data from the CoinGecko database.

## Installation
To run this project, it must be imported and compiled in Eclipse IDE.
* Import the project by selecting `Import > Existing Maven Projects`
* Select the `cryptoTrader\` directory to import, ensuring the `pom.xml` file is detected.
* Locate the file **LoginUI.java** located at `cryptoTrader\src\main\java\cryptoTrader\gui\LoginUI.java`
* Right-click the file and select `Run As > Java Application`. A Java application window should open.

## Login
The text file **login_db.txt** located at `cryptoTrader\src\main\java\cryptoTrader\login_db.txt` contains the combination of usernames and passwords accepted by the system.
To login, use the following sample combination:
* **Username:** admin
* **Password:** pass

## Usage
The system accepts multiple trading client actions containing a trading client name, coin list, and selected strategy.
The following input is a sample trading client action:
* **Trading Client:** trader-1
* **Coin List:** BTC,ADA,ETH
* **Strategy Name:** Strategy-A

To avoid compilation error, the cryptocurrency coin list must be written using only abbreviated capital letters, separated by commas with no spaces.\
**Correct Input**: BTC,ADA,ETH\
**Incorrect Input:** bTc, Ada, etheruem
