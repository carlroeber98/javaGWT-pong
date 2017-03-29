package com.carl.pongspiel.client.model;

public enum Color {
	RED{
		@Override
		public String getColor(){
			return "red";
		}
	},
	BLUE{
		@Override
		public String getColor(){
			return "blue";
		}
	},
	GREEN{
		@Override
		public String getColor(){
			return "green";
		}
	},
	YELLOW{
		@Override
		public String getColor(){
			return "yellow";
		}
	},
	BLACK{
		@Override
		public String getColor(){
			return "black";
		}
	},
	WHITE{
		@Override
		public String getColor(){
			return "white";
		}
	},
	ORANGE{
		@Override
		public String getColor(){
			return "orange";
		}
	},
	PURPLE{
		@Override
		public String getColor(){
			return "purple";
		}
	};
	
	public abstract String getColor();
	
}
