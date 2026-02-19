import 'dart:async';
import 'package:flutter/material.dart';

void main() {
  runApp(const BigClockApp());
}

class BigClockApp extends StatelessWidget {
  const BigClockApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: BigClockScreen(),
    );
  }
}

class BigClockScreen extends StatefulWidget {
  const BigClockScreen({super.key});

  @override
  State<BigClockScreen> createState() => _BigClockScreenState();
}

class _BigClockScreenState extends State<BigClockScreen> {
  late Timer _timer;
  DateTime _now = DateTime.now();

  @override
  void initState() {
    super.initState();
    _timer = Timer.periodic(const Duration(seconds: 1), (_) {
      setState(() {
        _now = DateTime.now();
      });
    });
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final time =
        "${_now.hour.toString().padLeft(2, '0')}${_now.minute.toString().padLeft(2, '0')}";

    return Scaffold(
      backgroundColor: Colors.black,
      body: Center(
        child: Text(
          time,
          textAlign: TextAlign.center, // ✅ تنصيف أفقي
          style: const TextStyle(
            fontSize: 250,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
    );
  }
}
