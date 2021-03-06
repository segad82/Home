'use strict'

const User = require('../models/user');
const service = require('../services/tocken');
const assert = require('assert');

function signUp (req, res) {
    const user = new User({
        email : req.body.email,
        displayName : req.body.displayName,
        password: req.body.password
    });

    user.save((err, data) => {
        if(err) return res.status(500).send({message: `Error al guardar usuario: ${err}`});
        service.createToken(data)
        .then(token => {
            res.status(201).send({ token });
        })
        .catch(err => {
            res.status(err.status).send(err.message);
        });
    });
}

function signIn (req, res) {
    console.log({data: req.body});
    User.find({ email: req.body.email }, (err, data) => {
        if(err) return res.status(500).send({message: `Error al buscar usuario: ${err}`});
        if(!data) return res.status(401).send({message: 'Usuario no existe'});
        console.log({data});
        service.createToken(data)
        .then(token => {
            res.status(200).send(token);
        })
        .catch(err => {
            res.status(err.status).send(err.message);
        });
    });
}

module.exports = {
  signUp,
  signIn
}