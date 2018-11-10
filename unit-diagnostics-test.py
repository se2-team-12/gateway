import unittest
import battery
import cpuCount
import availableMem
import timestamp
import osTest
import freeMem


class TestDiagnostics(unittest.TestCase):

    def test_osTest(self):
        self.assertEqual(type(osTest.os), str)

    def test_timestamp(self):
        self.assertEqual(type(timestamp.timestamp), float)

    def test_memory(self):
        self.assertEqual(type(availableMem.available_mem), int)

    def test_cpuCount(self):
        self.assertEqual(type(cpuCount.cpu_count), int)

    def test_battery(self):
        self.assertEqual(type(battery.battery), int)

    def test_battery(self):
        self.assertEqual(type(freeMem.free_mem), int)

if __name__ == '__main__':
    unittest.main()