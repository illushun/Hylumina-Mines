public class BlockBreak implements Listener {
    private Map<String, Integer> areaBlocksBroken = Maps.newHashMap();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player ply = e.getPlayer();
        Block block = e.getBlock();
        Location playerLocation = ply.getLocation();

        for (String areaName : Area.GetAreas()) {
            CuboidRegion region = new Area(areaName).GetRegion();

            if (region.contains(BlockVector3.at(block.getX(), block.getY(), block.getZ()))) {
                if (!areaBlocksBroken.containsKey(areaName)) {
                    areaBlocksBroken.put(areaName, 1);
                }
                else
                    areaBlocksBroken.put(areaName, areaBlocksBroken.get(areaName) + 1);

                Integer blocksBroken = areaBlocksBroken.get(areaName);
                Long areaVolume = region.getVolume();

                if (GetPercentage(Double.parseDouble(blocksBroken.toString()), areaVolume.doubleValue()) >= 60) {
                    new Area(areaName).ResetArea();
                    areaBlocksBroken.remove(areaName);
                }
            }
        }
    }

    private Double GetPercentage(double input, double max) {
        return ((input / max) * 100);
    }
}
