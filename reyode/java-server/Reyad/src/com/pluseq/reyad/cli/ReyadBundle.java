package com.pluseq.reyad.cli;

import java.io.File;
import java.util.Properties;

import com.pluseq.reyad.*;
import com.pluseq.reyad.bundle.*;
import com.pluseq.reyad.jdbc.StorageJdbc;
import com.pluseq.reyad.sql.DbInitilizer;
import com.pluseq.tay.SqlConnectionInterface;

import gnu.getopt.Getopt;

public class ReyadBundle {
	protected static StorageJdbc s;
	
	protected static void showHelp() {
		System.out.println("Usage:\n" +
				" reyad_bundle -i     : initialize\n" +
				" reyad filename      : apply bundle\n" +
				" reyad -h            : help\n");			
	}
	
	public static void main(String[] args) throws Exception {
		(new Bootstrap()).init();

        // if no arguments present - run in console mode
        if (args.length == 0) {
                showHelp();
                return;
        }

		
		Getopt g = new Getopt("reyad", args, "-ih");
		int c;
		while ((c = g.getopt()) != -1) {
			switch (c) {
				case 'h':
					showHelp();
					return;
				case 'i':
					(new DbInitilizer()).initializeAll();
					return;
				default:
					if (args.length > 0) {
						BundleManager bm = new BundleManager();
						Bundle b = bm.fromPropertyFile(new File(args[0]));
						
						BundleApplier a = new BundleApplier(s.getConnection());
						a.apply(b);
					} else {
						showHelp();
					}
			}
		}
	}
}
