package 'BatterySystem' {
	private import ScalarValues::*;
	
	attribute def TurnSOS;

	// Definicao da bateria
	part def Battery {
		attribute charge : Real;
		attribute sosTrigger : Real;
		attribute rechargeTrigger : Real;
		attribute consumeBattery : Real = 1;
		attribute chargeValue : Real = 5;
		attribute minCharge : Real = 0;
		attribute maxCharge : Real = 100;
		attribute keepOnValue : Real = 10;
		attribute keepRechargeValue : Real = 90;
	}
	
	// Acoes
	action MonitorBattery { out charge : Real; }
	attribute def TurnRecharge;
	attribute def BatteryDepleted;
	attribute def TurnOn;
	// Maquina de estados da bateria
	state BatteryStates {
		in battery : Battery;
		
		entry; then on; // Estado inicial
		do MonitorBattery;
		state on{
			entry assign battery.charge := battery.charge - battery.consumeBattery; //battery minus 1 loop
		}
		accept TurnSOS
			if MonitorBattery.charge >= battery.sosTrigger //TurnSOS (bateria>=25)
			do then SOS;
		accept BatteryDepleted
			if MonitorBattery.charge == battery.minCharge //BatteryDepleted [battery == 0]
			do then off;	
		accept
			when MonitorBattery.charge > battery.keepOnValue //Keeps on while battery > 10
			then on;
		
		// Estado 'SOS'
		state SOS; then off;
		// Estado 'off'
		state off; 
		
		accept TurnRecharge
			if MonitorBattery.charge <= battery.rechargeTrigger //TurnRecharge (battery<=20)
			do then Recharge;
		accept TurnOn
			if MonitorBattery.charge >= battery.minCharge //TurnOn [bateria > 0]
			do then on;
			
		// Estado 'Recharge'
		state Recharge{
			entry assign battery.charge := battery.charge + battery.chargeValue; // battery = battery + 5
		}
		accept TurnOn
			if MonitorBattery.charge >= battery.maxCharge
			do then on;
		accept
			when MonitorBattery.charge < battery.keepRechargeValue  //Keeps on while battery > 10
			then Recharge;
	}
}
