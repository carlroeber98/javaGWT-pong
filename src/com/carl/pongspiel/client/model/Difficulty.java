package com.carl.pongspiel.client.model;

public enum Difficulty {
	
	VERYEASY{
		@Override
		public int getbatSpeed(){
			return 20;
		}
		@Override
		public int getballSpeed(){
			return 30;
		}
		@Override
		public String getName() {
			return "Sehr leicht";
		}},
	
	EASY{
		@Override
		public int getbatSpeed(){
			return 18;
		}
		@Override
		public int getballSpeed() {
			return 20;
		}
		@Override
		public String getName() {
			return "Leicht";
		}},
	
	MEDIUM{
		@Override
		public int getbatSpeed(){
			return 10;
		}	
		@Override
		public int getballSpeed() {
			return 10;
		}
		@Override
		public String getName() {
			return "Mittel";
		}},

	HEAVY{
		@Override
		public int getbatSpeed(){
			return 5;
		}
		@Override
		public int getballSpeed() {
			return 5;
		}
		@Override
		public String getName() {
			return "Schwer";
		}},
	
	VERYHEAVY{
		@Override
		public int getbatSpeed(){
			return 3;
		}
		@Override
		public int getballSpeed() {
			return 1;
		}
		@Override
		public String getName() {
			return "Sehr schwer";
		}
	};
	
	public abstract int getballSpeed(); 
	public abstract int getbatSpeed();
	public abstract String getName();
	
}
