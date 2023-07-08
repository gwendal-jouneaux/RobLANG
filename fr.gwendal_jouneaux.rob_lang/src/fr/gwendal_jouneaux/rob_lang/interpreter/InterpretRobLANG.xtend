package fr.gwendal_jouneaux.rob_lang.interpreter

import fr.gwendal_jouneaux.rob_lang.interpreter.runtime.FunctionReturn
import fr.gwendal_jouneaux.rob_lang.interpreter.runtime.LoopBreak
import fr.gwendal_jouneaux.rob_lang.interpreter.runtime.RuntimeArray
import fr.gwendal_jouneaux.rob_lang.robLANG.And
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayAdd
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayGet
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayLength
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayNew
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayRemove
import fr.gwendal_jouneaux.rob_lang.robLANG.ArraySet
import fr.gwendal_jouneaux.rob_lang.robLANG.Assignment
import fr.gwendal_jouneaux.rob_lang.robLANG.Block
import fr.gwendal_jouneaux.rob_lang.robLANG.BoolConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Break
import fr.gwendal_jouneaux.rob_lang.robLANG.ComplexFunction
import fr.gwendal_jouneaux.rob_lang.robLANG.Condition
import fr.gwendal_jouneaux.rob_lang.robLANG.Divide
import fr.gwendal_jouneaux.rob_lang.robLANG.DoubleConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Equality
import fr.gwendal_jouneaux.rob_lang.robLANG.Expression
import fr.gwendal_jouneaux.rob_lang.robLANG.FunCall
import fr.gwendal_jouneaux.rob_lang.robLANG.FunDefinition
import fr.gwendal_jouneaux.rob_lang.robLANG.FunParamCapture
import fr.gwendal_jouneaux.rob_lang.robLANG.FunParamExp
import fr.gwendal_jouneaux.rob_lang.robLANG.Greater
import fr.gwendal_jouneaux.rob_lang.robLANG.GreaterEq
import fr.gwendal_jouneaux.rob_lang.robLANG.Inequality
import fr.gwendal_jouneaux.rob_lang.robLANG.InlineFunction
import fr.gwendal_jouneaux.rob_lang.robLANG.IntConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Less
import fr.gwendal_jouneaux.rob_lang.robLANG.LessEq
import fr.gwendal_jouneaux.rob_lang.robLANG.Loop
import fr.gwendal_jouneaux.rob_lang.robLANG.MathAbs
import fr.gwendal_jouneaux.rob_lang.robLANG.MathCos
import fr.gwendal_jouneaux.rob_lang.robLANG.MathModulo
import fr.gwendal_jouneaux.rob_lang.robLANG.MathPow
import fr.gwendal_jouneaux.rob_lang.robLANG.MathSin
import fr.gwendal_jouneaux.rob_lang.robLANG.MathSqrt
import fr.gwendal_jouneaux.rob_lang.robLANG.Minus
import fr.gwendal_jouneaux.rob_lang.robLANG.MoveBackward
import fr.gwendal_jouneaux.rob_lang.robLANG.MoveForward
import fr.gwendal_jouneaux.rob_lang.robLANG.Multiply
import fr.gwendal_jouneaux.rob_lang.robLANG.Not
import fr.gwendal_jouneaux.rob_lang.robLANG.Or
import fr.gwendal_jouneaux.rob_lang.robLANG.Plus
import fr.gwendal_jouneaux.rob_lang.robLANG.PrintExpression
import fr.gwendal_jouneaux.rob_lang.robLANG.Return
import fr.gwendal_jouneaux.rob_lang.robLANG.Robot
import fr.gwendal_jouneaux.rob_lang.robLANG.SenseBattery
import fr.gwendal_jouneaux.rob_lang.robLANG.SenseCompass
import fr.gwendal_jouneaux.rob_lang.robLANG.SenseDistance
import fr.gwendal_jouneaux.rob_lang.robLANG.SensePosition
import fr.gwendal_jouneaux.rob_lang.robLANG.SenseTime
import fr.gwendal_jouneaux.rob_lang.robLANG.SetNominalSpeed
import fr.gwendal_jouneaux.rob_lang.robLANG.Statement
import fr.gwendal_jouneaux.rob_lang.robLANG.StringConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.SymbolRef
import fr.gwendal_jouneaux.rob_lang.robLANG.TurnLeft
import fr.gwendal_jouneaux.rob_lang.robLANG.TurnRight
import fr.gwendal_jouneaux.rob_lang.robLANG.Variable
import fr.gwendal_jouneaux.rob_lang.robLANG.impl.RobLANGFactoryImpl
import fr.gwendal_jouneaux.rob_lang.typing.RobLANGTypeComputer
import java.util.HashMap
import org.eclipse.emf.ecore.EObject

class InterpretRobLANG {
 	final RobLANGTypeComputer typer = new RobLANGTypeComputer()
 	static InterpretRobLANG instance = null
 	
 	private new(){}  
 	
 	def static InterpretRobLANG INSTANCE(){
 		return new InterpretRobLANG();
 	}

	// CONSTANT
	public def dispatch Object interpret(IntConstant e,ContextRobLANG context) {
		e.value
	}
	public def dispatch Object interpret(BoolConstant e,ContextRobLANG context) {
		Boolean::parseBoolean(e.value)
	}
	public def dispatch Object interpret(StringConstant e,ContextRobLANG context) {
		e.value
	}
	public def dispatch Object interpret(DoubleConstant e,ContextRobLANG context) {
		e.value
	}
	
	// ARITHMETIC
	
	public def dispatch Object interpret(Multiply e,ContextRobLANG context) {
		if (typer.isDoubleType(typer.typeFor(e))) {
			return (e.left.interpretAdapt(context) as Double) * (e.right.interpretAdapt(context) as Double)
		} else {
			return (e.left.interpretAdapt(context) as Integer) * (e.right.interpretAdapt(context) as Integer)
		}
	}
	
	public def dispatch Object interpret(Divide e,ContextRobLANG context) {
		if (typer.isDoubleType(typer.typeFor(e))) {
			return (e.left.interpretAdapt(context) as Double) / (e.right.interpretAdapt(context) as Double)
		} else {
			return (e.left.interpretAdapt(context) as Integer) / (e.right.interpretAdapt(context) as Integer)
		}
	}
	
	public def dispatch Object interpret(Minus e,ContextRobLANG context) {
		if (typer.isDoubleType(typer.typeFor(e))) {
			return (e.left.interpretAdapt(context) as Double) - (e.right.interpretAdapt(context) as Double)
		} else {
			return (e.left.interpretAdapt(context) as Integer) - (e.right.interpretAdapt(context) as Integer)
		}
	}
	public def dispatch Object interpret(Plus e,ContextRobLANG context) {
		if (typer.isStringType(typer.typeFor(e.left)) || typer.isStringType(typer.typeFor(e.right))){
			return e.left.interpretAdapt(context).toString + e.right.interpretAdapt(context).toString
		} else if (typer.isDoubleType(typer.typeFor(e))) {
			return (e.left.interpretAdapt(context) as Double) + (e.right.interpretAdapt(context) as Double)
		} else {
			return (e.left.interpretAdapt(context) as Integer) + (e.right.interpretAdapt(context) as Integer)
		}
	}
	
	// BOOLEAN
	public def dispatch Object interpret(Not e,ContextRobLANG context) {
		!(e.expression.interpretAdapt(context) as Boolean)
	}
	public def dispatch Object interpret(And e,ContextRobLANG context) {
		(e.left.interpretAdapt(context) as Boolean) && (e.right.interpretAdapt(context) as Boolean)
	}
	public def dispatch Object interpret(Or e,ContextRobLANG context) {
		(e.left.interpretAdapt(context) as Boolean) || (e.right.interpretAdapt(context) as Boolean)
	}
	
	// COMPARISON
	public def dispatch Object interpret(Equality e,ContextRobLANG context) {
		e.left.interpretAdapt(context) == e.right.interpretAdapt(context)
	}
	public def dispatch Object interpret(Inequality e,ContextRobLANG context) {
		e.left.interpretAdapt(context) != e.right.interpretAdapt(context)
	}
	
	public def dispatch Object interpret(GreaterEq e,ContextRobLANG context) {
		if (typer.isStringType(typer.typeFor(e.left))) {
			val left = e.left.interpretAdapt(context) as String
			val right = e.right.interpretAdapt(context) as String
			return left >= right
		} else {
			val left = e.left.interpretAdapt(context) as Number
			val right = e.right.interpretAdapt(context) as Number
			return left.doubleValue >= right.doubleValue
		}
	}
	
	public def dispatch Object interpret(Greater e,ContextRobLANG context) {
		if (typer.isStringType(typer.typeFor(e.left))) {
			val left = e.left.interpretAdapt(context) as String
			val right = e.right.interpretAdapt(context) as String
			return left > right
		} else {
			val left = e.left.interpretAdapt(context) as Number
			val right = e.right.interpretAdapt(context) as Number
			return left.doubleValue > right.doubleValue
		}
	}
	
	public def dispatch Object interpret(LessEq e,ContextRobLANG context) {
		if (typer.isStringType(typer.typeFor(e.left))) {
			val left = e.left.interpretAdapt(context) as String
			val right = e.right.interpretAdapt(context) as String
			return left <= right
		} else {
			val left = e.left.interpretAdapt(context) as Number
			val right = e.right.interpretAdapt(context) as Number
			return left.doubleValue <= right.doubleValue
		}
	}
	
	public def dispatch Object interpret(Less e,ContextRobLANG context) {
		if (typer.isStringType(typer.typeFor(e.left))) {
			val left = e.left.interpretAdapt(context) as String
			val right = e.right.interpretAdapt(context) as String
			return left < right
		} else {
			val left = e.left.interpretAdapt(context) as Number
			val right = e.right.interpretAdapt(context) as Number
			return left.doubleValue < right.doubleValue
		}
	}
	
	// VARIABLE AND FUNCTION
	public def dispatch Object interpret(SymbolRef e,ContextRobLANG context) {
		if(typer.isStringType(typer.typeFor(e.variable))) return context.get(e.variable.name) as String
		if(typer.isIntType(typer.typeFor(e.variable))) return context.get(e.variable.name) as Integer
		if(typer.isBoolType(typer.typeFor(e.variable))) return context.get(e.variable.name) as Boolean
		if(typer.isDoubleType(typer.typeFor(e.variable))) return context.get(e.variable.name) as Double
		if(typer.isArrayType(typer.typeFor(e.variable))) return context.get(e.variable.name) as RuntimeArray
		context.get(e.variable.name)
	}
	
	public def dispatch Object interpret(FunCall e,ContextRobLANG context) {
		val function = e.function
		
		// create the new context with param
		var params = new HashMap<String, Object>()
		for(var i = 0; i<function.varNames.length; i++){
			params.put(function.varNames.get(i).name,e.params.get(i).interpretAdapt(context))
		}
		context.pushContext(params)
		
		// interpret
		var res = null as Object
		if(function instanceof InlineFunction){
			res = function.expression.interpretAdapt(context)
		}
		if(function instanceof ComplexFunction){
			try{
				res = function.body.interpretAdapt(context)
			} catch (FunctionReturn ret){
				res = ret.toReturn
			}
		}
		
		// update captured value in the caller context
		var captured = new HashMap<String, Object>()
		for(var i = 0; i<e.params.length; i++){
			val param = e.params.get(i)
			if(param instanceof FunParamCapture){
				captured.put(param.variable.name,context.get(function.varNames.get(i).name))	
			}
		}
		
		context.popContext()
		context.addAll(captured)
		
		return res
	}
	
	public def dispatch Object interpret(FunParamCapture e,ContextRobLANG context) {
		context.get(e.variable.name)
	}
	
	public def dispatch Object interpret(FunParamExp e,ContextRobLANG context) {
		e.expr.interpretAdapt(context)
	}
	
	public def dispatch Object interpret(Block e, ContextRobLANG context) {
		var res = null as Object
		for(var i = 0; i < e.statements.length; i++){
			res = interpretAdapt(e.statements.get(i),context)
		}
		return res
	}
	
	public def dispatch Object interpret(Break e,ContextRobLANG context) {
		throw new LoopBreak()
	}
	
	public def dispatch Object interpret(Return e,ContextRobLANG context) {
		throw new FunctionReturn(e.expression.interpretAdapt(context))
	}
	
	// SENSORS
	
	public def dispatch Object interpret(SenseTime e,ContextRobLANG context) {
		context.getTime
	}
	
	public def dispatch Object interpret(SenseBattery e,ContextRobLANG context) {
		context.getBattery
	}
	
	public def dispatch Object interpret(SensePosition e,ContextRobLANG context) {
		val array = new RuntimeArray(1)
		val pos = context.getPosition()
		for (axis : pos) {
			array.add(axis)
		}
		return array
	}
	
	public def dispatch Object interpret(SenseDistance e,ContextRobLANG context) {
		context.getDistance(e.sensorIndex)
	}
	
	public def dispatch Object interpret(SenseCompass e,ContextRobLANG context) {
		context.getCompass
	}
	
	
	public def dispatch Object interpret(Expression e,ContextRobLANG context) {
		throw new Exception("AST Node not dispatched")
	}
	
	
	
	public def dispatch Object interpret(Variable e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context)
		context.put(e.name, value)
		return value
	}
	
	public def dispatch Object interpret(Assignment e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context)
		context.put(e.assignee.name, value)
		return value
	}
	
	public def dispatch Object interpret(PrintExpression e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context)
		// Used to synchronize the print statement according to its location in the code
		context.getTime
		println(value)
		return value
	}
	
	public def dispatch Object interpret(Condition e,ContextRobLANG context) {
		var cond = e.expression.interpretAdapt(context) as Boolean
		if(cond){
			return e.ifz.interpretAdapt(context)
		}else if(e.elsez !== null){
			return e.elsez.interpretAdapt(context)
		}
	}
	
	public def dispatch Object interpret(Loop e,ContextRobLANG context) {
		var last = null as Object
		try{
			while(e.expression.interpretAdapt(context) as Boolean){
				last = e.body.interpretAdapt(context)
			}
		} catch(LoopBreak brk) {
			last = null as Object
		}
		
		return last
	}
	
	// MOVEMENTS
	public def dispatch Object interpret(MoveForward e,ContextRobLANG context) {
		val dist = e.distance.interpretAdapt(context) as Number
		//System.out.println("Forward")
		context.moveRobot(dist.doubleValue)
		return null
	}
	
	public def dispatch Object interpret(MoveBackward e,ContextRobLANG context) {
		val dist = e.distance.interpretAdapt(context) as Number
		//System.out.println("Backward")
		context.moveRobot(- dist.doubleValue)
		return null
	}
	
	public def dispatch Object interpret(TurnLeft e,ContextRobLANG context) {
		val angle = e.angle.interpretAdapt(context) as Number
		context.turnRobot(angle.doubleValue)
		return null
	}
	
	public def dispatch Object interpret(TurnRight e,ContextRobLANG context) {
		val angle = e.angle.interpretAdapt(context) as Number
		context.turnRobot(- angle.doubleValue)
		return null
	}
	
	public def dispatch Object interpret(SetNominalSpeed e,ContextRobLANG context) {
		val speed = e.speed.interpretAdapt(context) as Number
		context.setNominalSpeed(speed.doubleValue)
		return null
	}
	
	
	// Arrays
	
	public def dispatch Object interpret(ArrayNew e,ContextRobLANG context) {
		return new RuntimeArray(e.dimension)
	}
	
	public def dispatch Object interpret(ArrayLength e,ContextRobLANG context) {
		val a = e.array.interpretAdapt(context) as RuntimeArray
		return a.length
	}
	
	public def dispatch Object interpret(ArrayGet e,ContextRobLANG context) {
		val a = e.array.interpretAdapt(context) as RuntimeArray
		return a.get(e.index.interpretAdapt(context) as Integer)
	}
	
	public def dispatch Object interpret(ArraySet e,ContextRobLANG context) {
		val a = e.array.interpretAdapt(context) as RuntimeArray
		a.set(e.index.interpretAdapt(context) as Integer, e.value.interpretAdapt(context))
		return null
	}
	
	public def dispatch Object interpret(ArrayAdd e,ContextRobLANG context) {
		val a = e.array.interpretAdapt(context) as RuntimeArray
		if(e.indexedValue === null){
			a.add(e.valueOrIndex.interpretAdapt(context))
		} else {
			a.add(e.valueOrIndex.interpretAdapt(context) as Integer, e.indexedValue.interpretAdapt(context))
		}
		return null
	}
	
	public def dispatch Object interpret(ArrayRemove e,ContextRobLANG context) {
		val a = e.array.interpretAdapt(context) as RuntimeArray
		a.remove(e.index.interpretAdapt(context) as Integer)
		return null
	}
	
	
	
	
	// Math
	
	public def dispatch Object interpret(MathSqrt e,ContextRobLANG context) {
		val square = e.expression.interpretAdapt(context) as Number
		val root = Math.sqrt(square.doubleValue)
		return root as Double
	}
	
	public def dispatch Object interpret(MathPow e,ContextRobLANG context) {
		val value = e.value.interpretAdapt(context) as Number
		val exp = e.exponant.interpretAdapt(context) as Number
		return Math.pow(value.doubleValue, exp.doubleValue) as Double
	}
	
	public def dispatch Object interpret(MathSin e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context) as Number
		val sin = Math.sin(Math.toRadians(value.doubleValue))
		return sin as Double
	}
	
	public def dispatch Object interpret(MathCos e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context) as Number
		val cos = Math.cos(Math.toRadians(value.doubleValue))
		return cos as Double
	}
	
	public def dispatch Object interpret(MathAbs e,ContextRobLANG context) {
		val value = e.expression.interpretAdapt(context) as Number
		val abs = Math.abs(value.doubleValue)
		return abs as Double
	}
	
	public def dispatch Object interpret(MathModulo e,ContextRobLANG context) {
		val lhs = e.lhs.interpretAdapt(context) as Number
		val rhs = e.rhs.interpretAdapt(context) as Number
		return (lhs.doubleValue % rhs.doubleValue) as Double
	}
	


	// Adaptation annotations

//	public def dispatch Object interpret(AnnotatedStatement e,ContextRobLANG context) {
//		val softgoalID = e.property.literal
//		val low = e.lowerBound.interpretAdapt(context) as Number
//		val high = e.higherBound.interpretAdapt(context) as Number
//		context.pushAdaptationBounds(softgoalID, low.doubleValue, high.doubleValue)
//		e.statement.interpretAdapt(context)
//		context.popAdaptationBounds(softgoalID)
//		return null
//	}



	

	public def dispatch Object interpret(Statement e, ContextRobLANG context) {
		
	}
	
	public def dispatch Object interpret(Robot e, ContextRobLANG context) {
		var res = null as Object
		for(var i = 0; i < e.functions.length; i++){
			var f = e.functions.get(i) as FunDefinition
			if(f.name == "Main"){
				val callMain = RobLANGFactoryImpl.eINSTANCE.createFunCall
				callMain.function = f
				res = interpretAdapt(callMain,context)
			}
		}
		return res
	}
	
	def Object interpretAdapt(EObject e, ContextRobLANG context){
		e.interpret(context)
	}
}