package fr.gwendal_jouneaux.rob_lang.typing

class ArrayType implements RobLANGType {
	
	RobLANGType type;
	int dimension;
	
	new(RobLANGType type, int dimension){
		this.type = type
		this.dimension = dimension
	}
	
	def RobLANGType afterAccess(){
		if(this.dimension < 2){
			return this.type
		}
		return new ArrayType(this.type, this.dimension-1)
	}
	
	override toString() { type.toString + "[" + this.dimension + "]" }
}