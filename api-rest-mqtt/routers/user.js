'use strict'

const userController = require('../controllers/user');
const userRouter = require('express').Router();

userRouter.post('/signin', userController.signIn);
userRouter.post('/signup', userController.signUp);

module.exports = userRouter;