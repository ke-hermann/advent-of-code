GRID_SERIAL_NUMBER = 57
class Fuelcell:

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.power_level = self.init_power_level()

    def init_power_level(self):
        rack_id = self.x + 10
        pw = rack_id * self.y
        pw += GRID_SERIAL_NUMBER
        pw *= rack_id
        pw = 0 if pw < 100 else int(str(pw)[2])
        pw -= 5
        return pw
