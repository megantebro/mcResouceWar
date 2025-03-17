package lobby.mcresoucewar;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;
import java.util.Random;

public class OutPostResouces {
    OutPost outPost;
    int resouceTier;

    public OutPostResouces(OutPost outPost) {
        this.outPost = outPost;

    }

    public void decideTier() {
        //中心から離れているほど資源が少ない
        int index = Mcresoucewar.outPosts.indexOf(outPost);
        int centerIndex = Mcresoucewar.outPosts.size() / 2; // 中央インデックス計算
        int distanceFromCenter = Math.abs(centerIndex - index); // 中心からの距離
        resouceTier = 4 - Math.min(distanceFromCenter, 4) - 1; // 最大値4でクランプ

    }

    public void startGenerateResouce() {

        //一秒ごとにランダムに資源を生成
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!(Math.random() >= 0.2)) {
                    double ramdom = Math.random();
                    switch (resouceTier) {
                        case 3:
                            if (ramdom > 0.65) {
                                setResouce(Material.DIAMOND_ORE);
                            } else if (ramdom > 0.4) {
                                setResouce(Material.GOLD_ORE);
                            } else {
                                setResouce(Material.IRON_ORE);
                            }
                            break;
                        case 2:
                            if (ramdom > 0.65) {
                                setResouce(Material.GOLD_ORE);
                            } else if (ramdom > 0.4) {
                                setResouce(Material.IRON_ORE);
                            } else {
                                setResouce(Material.BIRCH_WOOD);
                            }
                            break;
                        case 1:
                            if (ramdom > 0.65) {
                                setResouce(Material.IRON_ORE);
                            } else if (ramdom > 0.4) {
                                setResouce(Material.BIRCH_WOOD);
                            } else {
                                setResouce(Material.HAY_BLOCK);
                            }
                            break;
                        case 0:
                            if (ramdom > 0.5) {
                                setResouce(Material.HAY_BLOCK);
                            } else {
                                setResouce(Material.BIRCH_WOOD);
                            }
                            break;
                    }
                }
            }
        }.runTaskTimer(Mcresoucewar.plugin, 0L, 20L);
    }

    private void setResouce(Material material) {
        Random random = new Random();
        int x = outPost.x + random.nextInt(17);
        int z = outPost.z + random.nextInt(17);

        //鉱石発生場所に何もなかった場合再実行
        if (new Location(outPost.world, x, -57, z).getBlock().getType() == Material.AIR) {
            if (Math.random() > 0.1) {
                setResouce(material);
            }
            return;
        }
        new Location(outPost.world, x, -57, z).getBlock().setType(material);

    }
}
