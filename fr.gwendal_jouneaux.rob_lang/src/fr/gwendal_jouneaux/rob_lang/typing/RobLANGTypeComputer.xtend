package fr.gwendal_jouneaux.rob_lang.typing

import fr.gwendal_jouneaux.rob_lang.robLANG.And
import fr.gwendal_jouneaux.rob_lang.robLANG.BoolConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.ComplexFunction
import fr.gwendal_jouneaux.rob_lang.robLANG.Divide
import fr.gwendal_jouneaux.rob_lang.robLANG.DoubleConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Equality
import fr.gwendal_jouneaux.rob_lang.robLANG.Expression
import fr.gwendal_jouneaux.rob_lang.robLANG.FunCall
import fr.gwendal_jouneaux.rob_lang.robLANG.FunParamCapture
import fr.gwendal_jouneaux.rob_lang.robLANG.FunParamExp
import fr.gwendal_jouneaux.rob_lang.robLANG.Greater
import fr.gwendal_jouneaux.rob_lang.robLANG.GreaterEq
import fr.gwendal_jouneaux.rob_lang.robLANG.InlineFunction
import fr.gwendal_jouneaux.rob_lang.robLANG.IntConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Less
import fr.gwendal_jouneaux.rob_lang.robLANG.LessEq
import fr.gwendal_jouneaux.rob_lang.robLANG.Minus
import fr.gwendal_jouneaux.rob_lang.robLANG.Multiply
import fr.gwendal_jouneaux.rob_lang.robLANG.Not
import fr.gwendal_jouneaux.rob_lang.robLANG.Or
import fr.gwendal_jouneaux.rob_lang.robLANG.Parameter
import fr.gwendal_jouneaux.rob_lang.robLANG.Plus
import fr.gwendal_jouneaux.rob_lang.robLANG.PrintExpression
import fr.gwendal_jouneaux.rob_lang.robLANG.StringConstant
import fr.gwendal_jouneaux.rob_lang.robLANG.Symbol
import fr.gwendal_jouneaux.rob_lang.robLANG.SymbolRef
import fr.gwendal_jouneaux.rob_lang.robLANG.Type
import fr.gwendal_jouneaux.rob_lang.robLANG.Variable
import fr.gwendal_jouneaux.rob_lang.robLANG.ArrayGet

class RobLANGTypeComputer {
	public static val STRING_TYPE = new StringType
	public static val INT_TYPE = new IntType
	public static val BOOL_TYPE = new BoolType
	public static val DOUBLE_TYPE = new DoubleType
	public static val VOID_TYPE = new DoubleType



	def isStringType(RobLANGType type) {
		type === STRING_TYPE
	}

	def isIntType(RobLANGType type) {
		type === INT_TYPE
	}

	def isBoolType(RobLANGType type) {
		type === BOOL_TYPE
	}
	
	def isDoubleType(RobLANGType type) {
		type === DOUBLE_TYPE
	}
	
	def isArrayType(RobLANGType type) {
		type instanceof ArrayType
	}

	def dispatch RobLANGType typeFor(Expression e) {
		switch (e) {
			StringConstant: STRING_TYPE
			IntConstant: INT_TYPE
			BoolConstant: BOOL_TYPE
			DoubleConstant: DOUBLE_TYPE
			Not: BOOL_TYPE
			Greater: BOOL_TYPE
			GreaterEq: BOOL_TYPE
			Less: BOOL_TYPE
			LessEq: BOOL_TYPE
			Equality: BOOL_TYPE
			And: BOOL_TYPE
			Or: BOOL_TYPE
			default: null
		}
	}

	def dispatch RobLANGType typeFor(Plus e) {
		val leftType = e.left.typeFor
		val rightType = e.right?.typeFor
		if (leftType.isStringType || rightType.isStringType)
			STRING_TYPE
		else if (leftType.isDoubleType || rightType.isDoubleType)
			DOUBLE_TYPE
		else
			INT_TYPE
	}
	
	def dispatch RobLANGType typeFor(Minus e) {
		val leftType = e.left.typeFor
		val rightType = e.right?.typeFor
		if (leftType.isDoubleType || rightType.isDoubleType)
			DOUBLE_TYPE
		else
			INT_TYPE
	}
	
	def dispatch RobLANGType typeFor(Divide e) {
		val leftType = e.left.typeFor
		val rightType = e.right?.typeFor
		if (leftType.isDoubleType || rightType.isDoubleType)
			DOUBLE_TYPE
		else
			INT_TYPE
	}
	
	def dispatch RobLANGType typeFor(Multiply e) {
		val leftType = e.left.typeFor
		val rightType = e.right?.typeFor
		if (leftType.isDoubleType || rightType.isDoubleType)
			DOUBLE_TYPE
		else
			INT_TYPE
	}

	def dispatch RobLANGType typeFor(SymbolRef symbolRef) {
		if (symbolRef.variable === null)
			return null
		else
			return symbolRef.variable.typeFor
	}
	
	def dispatch RobLANGType typeFor(Symbol symbol) {
		switch(symbol){
			Variable: (symbol as Variable).typeFor
			Parameter: (symbol as Parameter).typeFor
		}
	}
	
	def dispatch RobLANGType typeFor(PrintExpression eval) {
		eval?.expression?.typeFor
	}
	
	def dispatch RobLANGType typeFor(Variable variable) {
		variable?.expression?.typeFor
	}
	
	def dispatch RobLANGType typeFor(Type type) {
		val basicType = type.type.getName
		val dimension  = type.dimension
		val isArray = dimension != 0
		var roblangType = null as RobLANGType
		if(basicType == "String") roblangType = STRING_TYPE
		if(basicType == "bool") roblangType = BOOL_TYPE
		if(basicType == "int") roblangType = INT_TYPE
		if(basicType == "double") roblangType = DOUBLE_TYPE
		if(basicType == "void") roblangType = VOID_TYPE
		if(isArray && roblangType !== null){
			roblangType = new ArrayType(roblangType, dimension)
		}
		return null
	}
	
	def dispatch RobLANGType typeFor(Parameter parameter) {
		return parameter.type.typeFor
	}
	
	def dispatch RobLANGType typeFor(ComplexFunction function) {
		function.type.typeFor
	}
	
	def dispatch RobLANGType typeFor(InlineFunction function) {
		function.type.typeFor
	}
	
	def dispatch RobLANGType typeFor(FunCall call) {
		call.function.typeFor
	}
	
	def dispatch RobLANGType typeFor(FunParamExp param) {
		param.expr.typeFor
	}
	
	def dispatch RobLANGType typeFor(FunParamCapture param) {
		param.variable.typeFor
	}
	
	def dispatch RobLANGType typeFor(ArrayGet arrayGet) {
		val arrayType = arrayGet.array.typeFor as ArrayType
		return arrayType.afterAccess
	}
}